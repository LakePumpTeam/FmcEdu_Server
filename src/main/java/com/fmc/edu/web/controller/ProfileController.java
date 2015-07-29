package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.exception.IdentityCodeException;
import com.fmc.edu.exception.InvalidIdException;
import com.fmc.edu.exception.LoginException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.*;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.model.push.PushMessageParameter;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.ValidationUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.RequestParameterBuilder;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Yove on 5/4/2015.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private static final Logger LOG = Logger.getLogger(ProfileController.class);

    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "requestParameterBuilder")
    private RequestParameterBuilder mParameterBuilder;

    @Resource(name = "responseBuilder")
    private ResponseBuilder mResponseBuilder;

    @Resource(name = "baiduPushManager")
    private BaiDuPushManager mBaiDuPushManager;

    @Resource(name = "pushMessageManager")
    private PushMessageManager mPushMessageManager;

    @RequestMapping(value = ("/requestPhoneIdentify" + GlobalConstant.URL_SUFFIX))
    @ResponseBody
    public String requestPhoneIdentify(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellPhone) throws IOException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String phone = cellPhone;
        LOG.debug("Decoded cellphone:" + phone);
        // output error if phone number is blank
        if (!ValidationUtils.isValidPhoneNumber(phone)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PHONE_ERROR, phone);
            return output(responseBean);
        }

        TransactionStatus status = ensureTransaction();
        try {
            String identifyCode = getProfileManager().obtainIdentityCode(phone);
            pRequest.getSession().setAttribute(SessionConstant.SESSION_KEY_PHONE, phone);

            if (WebConfig.isDevelopment()) {
                responseBean.addData("identifyCode", identifyCode);
                LOG.debug(String.format("Send identify code: %s to phone: %s", identifyCode, phone));
            }
        } catch (ProfileException ex) {
            responseBean.addBusinessMsg(ex.getMessage(), ex.getArgs());
            status.setRollbackOnly();
        } catch (IdentityCodeException ex) {
            responseBean.addBusinessMsg(ex.getMessage(), ex.getArgs());
            status.setRollbackOnly();
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }

        return output(responseBean);
    }

    @RequestMapping(value = "/requestRegisterConfirm" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestRegisterConfirm(final HttpServletRequest pRequest, final HttpServletResponse pResponse,
                                         String cellPhone,
                                         final String authCode,
                                         String password,
                                         String salt) throws IOException {
        String identifyingCode = authCode;
        String phoneNumber = cellPhone;
        String passwordDecode = password;
        String saltDecode = salt;

        ResponseBean responseBean = new ResponseBean(pRequest);
        preRequestRegisterConfirm(pRequest, phoneNumber, identifyingCode, passwordDecode, saltDecode, responseBean);
        if (!responseBean.isSuccess()) {
            LOG.debug("pre validation failed." + responseBean.toString());
            return output(responseBean);
        }

        TransactionStatus status = ensureTransaction();
        try {
            if (!getProfileManager().verifyIdentityCodeAndRegister(phoneNumber, passwordDecode, identifyingCode, saltDecode)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_IDENTITY_CODE_INVALID);
                return output(responseBean);
            }
        } catch (IdentityCodeException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            status.setRollbackOnly();
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            status.setRollbackOnly();
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
        } finally {
            getTransactionManager().commit(status);
            return output(responseBean);
        }
    }

    private void preRequestRegisterConfirm(HttpServletRequest pRequest, String cellPhone, final String authCode,
                                           String password, String pSalt, ResponseBean responseBean) {
        if (StringUtils.isBlank(cellPhone)) {
            cellPhone = (String) pRequest.getSession().getAttribute(SessionConstant.SESSION_KEY_PHONE);
        }
        if (!ValidationUtils.isValidPhoneNumber(cellPhone)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PHONE_ERROR, cellPhone);
            return;
        }
        if (StringUtils.isBlank(authCode)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_IDENTITY_CODE_EMPTY);
            return;
        }
        if (StringUtils.isBlank(password)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PASSWORD_EMPTY);
            return;
        }
        if (StringUtils.isBlank(pSalt)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_SALT_EMPTY);
            return;
        }
    }

    @RequestMapping(value = "/requestRegisterBaseInfo" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestRegisterBaseInfo(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellPhone)
            throws IOException {
        TransactionStatus status = ensureTransaction();
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {

            ParentProfile parent = getParameterBuilder().buildParent(pRequest, getMyAccountManager());

            getProfileManager().insertOrUpdateParentProfile(parent);

            Address address = getParameterBuilder().buildAddress(pRequest);
            if (address != null) {
                getProfileManager().registerParentAddress(parent.getId(), address);
                parent.setAddressId(address.getId());
            }
            Student student = getParameterBuilder().buildStudent(pRequest);
            ParentStudentRelationship relationship = getParameterBuilder().buildParentStudentRelationship(pRequest);
            relationship.setParentId(parent.getId());
            getProfileManager().registerRelationshipBetweenStudent(relationship, student, parent);
            getProfileManager().updateParentProfile(parent);
        } catch (InvalidIdException e) {
            responseBean.addBusinessMsg(e.getMessage());
            status.setRollbackOnly();
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            status.setRollbackOnly();
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
            return output(responseBean);
        }
    }

    @RequestMapping(value = "/requestSalt" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestSalt(HttpServletRequest pRequest,
                              final HttpServletResponse pResponse, final String cellPhone) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            String phone = cellPhone;
            if (!ValidationUtils.isValidPhoneNumber(phone)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PHONE_ERROR, cellPhone);
                return output(responseBean);
            }
            BaseProfile user = getMyAccountManager().findUser(phone);
            if (user == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, phone);
                return output(responseBean);
            }
            responseBean.addData("salt", user.getSalt());
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestLogin" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestLogin(HttpServletRequest pRequest,
                               final HttpServletResponse pResponse,
                               final String userAccount,
                               final String password) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        BaseProfile user = null;
        try {
            if (!ValidationUtils.isValidPhoneNumber(userAccount)) {
                throw new LoginException(ResourceManager.VALIDATION_USER_PHONE_ERROR, userAccount);
            }
            if (com.fmc.edu.util.StringUtils.isBlank(password)) {
                throw new LoginException(ResourceManager.VALIDATION_USER_NEW_PASSWORD_EMPTY, password);
            }
            user = getMyAccountManager().loginUser(userAccount, password);
        } catch (LoginException e) {
            LOG.debug("Login failed:" + getResourceManager().getMessage(pRequest, e.getMessage(), e.getArgs()));
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
        }
        if (user != null && responseBean.isSuccess()) {
            getResponseBuilder().buildAuthorizedResponse(responseBean, user);
        }

        return output(responseBean);
    }

    @RequestMapping(value = "/requestLogout" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestLogout(HttpServletRequest pRequest,
                                final HttpServletResponse pResponse,
                                final String userAccount,
                                final String password) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        BaseProfile user = null;
        TransactionStatus txStatus = ensureTransaction();
        try {
            String userId = pRequest.getParameter("userId");
            if (!RepositoryUtils.idIsValid(userId)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userId);
                return output(responseBean);
            }
            user = getMyAccountManager().findUserById(userId);
            if (user == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userId);
                return output(responseBean);
            }
            user.setOnline(false);
            getMyAccountManager().updateBaseProfile(user);
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestForgetPwd" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestForgetPwd(HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        preRequestForgetPwd(pRequest, responseBean);
        if (!responseBean.isSuccess()) {
            return responseBean.toString();
        }
        TransactionStatus txStatus = ensureTransaction();
        try {
            String cellPhone = pRequest.getParameter("cellPhone");
            String authCode = pRequest.getParameter("authCode");
            String password = pRequest.getParameter("password");

            if (!ValidationUtils.isValidPhoneNumber(cellPhone)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PHONE_ERROR, cellPhone);
                return output(responseBean);
            }
            BaseProfile user = getMyAccountManager().findUser(cellPhone);
            if (user == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, cellPhone);
                return output(responseBean);
            }
            if (!getProfileManager().verifyIdentityCode(user.getPhone(), authCode)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_IDENTITY_CODE_INVALID);
                return output(responseBean);
            }
            if (!(getMyAccountManager().resetPassword(user, password) > 0)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_RESET_PASSWORD_FAILED);
                return output(responseBean);
            }
        } catch (IdentityCodeException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            txStatus.setRollbackOnly();
        } catch (Exception ex) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(ex);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    private void preRequestForgetPwd(HttpServletRequest pRequest, ResponseBean pResponseBean) {
        String cellPhone = pRequest.getParameter("cellPhone");
        if (StringUtils.isBlank(cellPhone)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PHONE_EMPTY);
            return;
        }
        String authCode = pRequest.getParameter("authCode");
        if (StringUtils.isBlank(authCode)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_IDENTITY_CODE_EMPTY);
            return;
        }
        String password = pRequest.getParameter("password");
        if (StringUtils.isBlank(password)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PASSWORD_EMPTY);
            return;
        }
    }

    @RequestMapping(value = "/requestAlterPwd" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestAlterPwd(HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        preRequestAlterPwd(pRequest, responseBean);
        if (!responseBean.isSuccess()) {
            return output(responseBean);
        }
        TransactionStatus txStatus = ensureTransaction();

        try {
            String userId = pRequest.getParameter("userId");
            String oldPassword = pRequest.getParameter("oldPassword");
            String newPassword = pRequest.getParameter("newPassword");


            BaseProfile user = getMyAccountManager().findUserById(userId);
            if (user == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userId);
                return output(responseBean);
            }

            if (!user.getPassword().equals(oldPassword)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_OLD_PASSWORD_ERROR);
                return output(responseBean);
            }
            getMyAccountManager().resetPassword(user, newPassword);
        } catch (Exception ex) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(ex);
        } finally {
            getTransactionManager().commit(txStatus);
            return output(responseBean);
        }
    }

    private void preRequestAlterPwd(HttpServletRequest pRequest, ResponseBean pResponseBean) {
        String userId = pRequest.getParameter("userId");
        if (StringUtils.isBlank(userId)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_EMPTY);
            return;
        }
        String oldPassword = pRequest.getParameter("oldPassword");
        if (StringUtils.isBlank(oldPassword)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_OLD_PASSWORD_ERROR);
            return;
        }
        String newPassword = pRequest.getParameter("newPassword");
        if (StringUtils.isBlank(newPassword)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_NEW_PASSWORD_EMPTY);
            return;
        }
    }

    @RequestMapping(value = "/requestGetRelateInfo" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestGetRelateInfo(HttpServletRequest pRequest, final HttpServletResponse pResponse)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String parentId = pRequest.getParameter("parentId");
        if (StringUtils.isBlank(parentId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, parentId);
            return output(responseBean);
        }
        int id = Integer.valueOf(parentId);
        try {

            ParentProfile parent = getProfileManager().queryParentDetailById(id);
            Address address = parent.getAddress();
            responseBean.addData("cellPhone", parent.getPhone());
            responseBean.addData("provId", address.getProvinceId());
            responseBean.addData("provName", address.getProvince());
            responseBean.addData("cityId", address.getCityId());
            responseBean.addData("cityName", address.getCity());
            Student student = parent.getStudent();
            responseBean.addData("schoolId", student.getSchool().getId());
            responseBean.addData("schoolName", student.getSchool().getName());
            responseBean.addData("classId", student.getFmcClass().getId());
            responseBean.addData("className", getSchoolManager().getClassString(student.getFmcClass().getGrade(), student.getFmcClass()
                    .getRealClass()));
            responseBean.addData("teacherId", student.getFmcClass().getHeadTeacherId());
            responseBean.addData("teacherName", student.getFmcClass().getHeadTeacherName());
            responseBean.addData("studentId", student.getId());
            responseBean.addData("studentName", student.getName());
            responseBean.addData("studentSex", student.isMale());
            responseBean.addData("studentBirth", DateUtils.convertDateToString(student.getBirth()));
            responseBean.addData("parentName", parent.getName());
            responseBean.addData("relation", student.getParentStudentRelationship().getRelationship());
            responseBean.addData("address", address.getAddress());
            responseBean.addData("addressId", address.getId());
            responseBean.addData("braceletCardNumber", student.getRingPhone());
            responseBean.addData("braceletNumber", student.getRingNumber());
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
            LOG.error(ex);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestParentAudit" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestParentAudit(HttpServletRequest pRequest,
                                     final HttpServletResponse pResponse,
                                     final String teacherId,
                                     final String[] parentIds,
                                     String setPass) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (ArrayUtils.isEmpty(parentIds)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_PARENT_ID_ERROR);
            return output(responseBean);
        }
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, teacherId);
            return output(responseBean);
        }
        TransactionStatus status = ensureTransaction();
        try {
            LOG.debug("requestParentAudit():parentIds:" + parentIds);
            int[] ids = decodeInputIds(parentIds);
            LOG.debug("requestParentAudit():decode parentIds:" + ids);
            int tid = Integer.valueOf(teacherId);
            LOG.debug("requestParentAudit():decode teacherId:" + tid);
            int pass = Integer.valueOf(setPass);
            LOG.debug("requestParentAudit():decode setPass:" + pass);
            getMyAccountManager().updateParentAuditStatus(tid, ids, pass);
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
            return output(responseBean);
        }
    }

    @RequestMapping(value = "/requestParentAuditAll" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestParentAuditAll(HttpServletRequest pRequest, final HttpServletResponse pResponse,
                                        final String teacherId,
                                        final String allPass) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, teacherId);
            return output(responseBean);
        }

        TransactionStatus status = ensureTransaction();
        try {
            int tid = Integer.valueOf(teacherId);
            int pass = Integer.valueOf(allPass);
            getMyAccountManager().updateAllParentAuditStatus(tid, pass);
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestPendingAuditParentList" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestPendingAuditParents(HttpServletRequest pRequest,
                                             final HttpServletResponse pResponse,
                                             final String teacherId) throws IOException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, teacherId);
            return output(responseBean);
        }

        int tid = Integer.valueOf(teacherId);
        List<Map<String, Object>> pendingAuditParents = getMyAccountManager().getPendingAuditParents(tid);
        responseBean.addData("parentsAuditList", pendingAuditParents);
        return output(responseBean);
    }

    @RequestMapping(value = "/bindBaiDuPush" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String bindBaiDuPush(HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String userId = pRequest.getParameter("userId");
        String channelId = pRequest.getParameter("channelId");
        String baiduUserId = pRequest.getParameter("baiduUserId");
        String deviceType = pRequest.getParameter("deviceType");
        if (!RepositoryUtils.idIsValid(userId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR);
            return output(responseBean);
        }
        //FIXME We should remove baidu information when app send blank channelId and  BaiduUserId, make sure we should not push message to this device again
       /* if (StringUtils.isBlank(channelId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_BAIDU_CHANNEL_ID_ERROR);
            return output(responseBean);
        }
        if (StringUtils.isBlank(baiduUserId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_BAIDU_USER_ID_ERROR);
            return output(responseBean);
        }

        if (StringUtils.isBlank(deviceType) || !DeviceType.isValidDeviceType(Integer.valueOf(deviceType))) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_DEVICE_TYPE_ERROR, deviceType);
            return output(responseBean);
        }
        */

        if (StringUtils.isBlank(channelId)) {
            channelId = "-";
        }
        if (StringUtils.isBlank(baiduUserId)) {
            baiduUserId = "-";
        }
        if (StringUtils.isBlank(deviceType) || !DeviceType.isValidDeviceType(Integer.valueOf(deviceType))) {
            deviceType = "-1";
        }
        BaseProfile user = getMyAccountManager().findUserById(userId);
        if (user == null) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userId);
            return output(responseBean);
        }

        user.setChannelId(channelId);
        user.setAppId(baiduUserId);
        user.setDeviceType(Integer.valueOf(deviceType));
        TransactionStatus status = ensureTransaction();
        try {
            getMyAccountManager().updateBaseProfile(user);
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }

        return output(responseBean);
    }

    @RequestMapping(value = "/pushMsg" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String pushMsg(HttpServletRequest pRequest) {
        String phone = pRequest.getParameter("phone");
        String title = pRequest.getParameter("title");
        String content = pRequest.getParameter("content");
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (com.fmc.edu.util.StringUtils.isBlank(phone)) {
            responseBean.addErrorMsg("phone is empty");
            return output(responseBean);
        }
        if (com.fmc.edu.util.StringUtils.isBlank(title)) {
            responseBean.addErrorMsg("phone is empty");
            return output(responseBean);
        }
        if (com.fmc.edu.util.StringUtils.isBlank(content)) {
            responseBean.addErrorMsg("content is empty");
            return output(responseBean);
        }

        BaseProfile user = getMyAccountManager().findUser(phone);
        if (user == null || com.fmc.edu.util.StringUtils.isBlank(user.getChannelId()) || !DeviceType.isValidDeviceType(user.getDeviceType())) {
            responseBean.addErrorMsg("Pushing message failed.");
            return output(responseBean);
        }
        try {
            boolean isSuccess = getBaiDuPushManager().pushNotificationMsg(user.getDeviceType(), new String[]{user.getChannelId()}, user.getId(), new PushMessageParameter(title, content));
            responseBean.addData("isSuccess", isSuccess);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.addErrorMsg(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/queryPushMessage" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String queryPushMessage(HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        Pagination pagination;
        try {
            pagination = buildPagination(pRequest);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            return output(responseBean);
        }
        String userId = pRequest.getParameter("userId");
        if (!RepositoryUtils.idIsValid(userId)) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER);
            return output(responseBean);
        }

        List<PushMessage> pushMessages = getPushMessageManager().queryAllPushMessageByProfileId(Integer.valueOf(userId), pagination);
        if (CollectionUtils.isEmpty(pushMessages)) {
            responseBean.addData("pushMessage", Collections.EMPTY_LIST);
            return output(responseBean);
        }
        List<Map<String, Object>> pushMessageList = new ArrayList<Map<String, Object>>(pushMessages.size());
        Map<String, Object> message;
        for (PushMessage pushMessage : pushMessages) {
            if (pushMessage == null) {
                continue;
            }
            message = new HashMap<String, Object>(3);
            message.put("title", pushMessage.getTitle());
            message.put("content", pushMessage.getContent());
            message.put("date", pushMessage.getCreationDate());
            pushMessageList.add(message);
        }
        responseBean.addData("pushMessages", pushMessageList);
        return output(responseBean);
    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(final ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(final SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }

    public RequestParameterBuilder getParameterBuilder() {
        return mParameterBuilder;
    }

    public void setParameterBuilder(final RequestParameterBuilder pParameterBuilder) {
        mParameterBuilder = pParameterBuilder;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public ResponseBuilder getResponseBuilder() {
        return mResponseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
        mResponseBuilder = pResponseBuilder;
    }

    public BaiDuPushManager getBaiDuPushManager() {
        return mBaiDuPushManager;
    }

    public void setBaiDuPushManager(BaiDuPushManager pBaiDuPushManager) {
        mBaiDuPushManager = pBaiDuPushManager;
    }

    public PushMessageManager getPushMessageManager() {
        return mPushMessageManager;
    }

    public void setPushMessageManager(PushMessageManager pPushMessageManager) {
        mPushMessageManager = pPushMessageManager;
    }
}
