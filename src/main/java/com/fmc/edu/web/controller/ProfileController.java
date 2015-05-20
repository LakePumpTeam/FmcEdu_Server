package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.exception.IdentityCodeException;
import com.fmc.edu.exception.InvalidIdException;
import com.fmc.edu.exception.LoginException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.IdentityCodeManager;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.ValidationUtils;
import com.fmc.edu.web.RequestParameterBuilder;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private static final Logger LOG = Logger.getLogger(ProfileController.class);

    private static final String ERROR_SESSION_EXPIRED = "Sorry, the session has expired.";
    private static final String ERROR_NULL_PARENT_ID = "Sorry, the parent id should not be empty.";
    private static final String ERROR_NULL_PARENT_IDS = "Sorry, the parent ids should not be empty.";

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


    @RequestMapping(value = ("/requestPhoneIdentify" + GlobalConstant.URL_SUFFIX))
    @ResponseBody
    public String requestPhoneIdentify(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellPhone) throws IOException {
        ResponseBean responseBean = new ResponseBean();
        String phone = decodeInput(cellPhone);
        LOG.debug("Decoded cellphone:" + phone);
        // output error if phone number is blank
        if (cellPhone == null || ValidationUtils.isValidPhoneNumber(phone)) {
            responseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_PHONE);
            return output(responseBean);
        }
        String identifyCode = null;
        TransactionStatus status = ensureTransaction();
        try {
            identifyCode = getProfileManager().obtainIdentityCode(phone);
            pRequest.getSession().setAttribute(SessionConstant.SESSION_KEY_PHONE, phone);

            if (WebConfig.isDevelopment()) {
                responseBean.addData("identifyCode", identifyCode);
                LOG.debug(String.format("Send identify code: %s to phone: %s", identifyCode, phone));
            }
        } catch (ProfileException ex) {
            responseBean.addBusinessMsg(ex.getMessage());
            status.setRollbackOnly();
        } catch (IdentityCodeException ex) {
            responseBean.addBusinessMsg(ex.getMessage());
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
    public String requestRegisterConfirm(final HttpServletRequest pRequest, final HttpServletResponse pResponse, String cellPhone, final String authCode,
                                         String password, String pSalt) throws IOException {
        String identifyingCode = decodeInput(authCode);
        String phoneNumber = decodeInput(cellPhone);
        String passwordDecode = decodeInput(password);
        String salt = decodeInput(pSalt);

        ResponseBean responseBean = new ResponseBean();
        preRequestRegisterConfirm(pRequest, identifyingCode, phoneNumber, passwordDecode, salt, responseBean);
        if (!responseBean.isSuccess()) {
            LOG.debug("pre validation failed." + responseBean.toString());
            return output(responseBean);
        }

        TransactionStatus status = ensureTransaction();
        try {
            if (!getProfileManager().verifyIdentityCodeAndRegister(phoneNumber, passwordDecode, identifyingCode, salt)) {
                responseBean.addBusinessMsg(IdentityCodeManager.ERROR_INVALID_IDENTITY_CODE);
                return responseBean.toString();
            }
        } catch (IdentityCodeException e) {
            responseBean.addBusinessMsg(e.getMessage());
            status.setRollbackOnly();
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage());
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
        if (cellPhone == null || ValidationUtils.isValidPhoneNumber(cellPhone)) {
            responseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_PHONE);
            return;
        }
        if (StringUtils.isBlank(authCode)) {
            responseBean.addBusinessMsg(IdentityCodeManager.ERROR_INVALID_EMPTY_AUTHO_CODE);
            return;
        }
        if (StringUtils.isBlank(password)) {
            responseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_EMTPY_PASSWORD);
            return;
        }
        /*if (StringUtils.isBlank(pSalt)) {
            responseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_EMPTY_SALT);
            return;
        }*/
    }

    @RequestMapping(value = "/requestRegisterBaseInfo" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestRegisterBaseInfo(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellPhone)
            throws IOException {
        TransactionStatus status = ensureTransaction();
        ResponseBean responseBean = new ResponseBean();
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
            responseBean.addBusinessMsg(e.getMessage());
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
    public String requestSalt(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String pUserId) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String userId = decodeInput(pUserId);
            if (StringUtils.isBlank(userId)) {
                responseBean.addBusinessMsg("user id is null.");
                return output(responseBean);
            }
            BaseProfile user = getMyAccountManager().findUser(userId);
            if (user == null) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
                return output(responseBean);
            }
            responseBean.addData("salt", user.getSalt());
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestLogin" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestLogin(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String userAccount, final String password) {
        ResponseBean responseBean = new ResponseBean();
        BaseProfile user = null;
        try {
            String account = decodeInput(userAccount);
            String pwd = decodeInput(password);
            user = getMyAccountManager().loginUser(account, pwd);
        } catch (LoginException e) {
            LOG.debug("Login failed:" + e.getMessage());
            responseBean.addBusinessMsg(e.getMessage());
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
        }
        if (user != null && responseBean.isSuccess()) {
            getResponseBuilder().buildAuthorizedResponse(responseBean, user);
        }

        return output(responseBean);
    }

    @RequestMapping(value = "/requestForgetPwd" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestForgetPwd(HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean();
        preRequestForgetPwd(pRequest, responseBean);
        if (!responseBean.isSuccess()) {
            return responseBean.toString();
        }
        TransactionStatus txStatus = ensureTransaction();
        try {
            String cellPhone = decodeInput(pRequest.getParameter("cellPhone"));
            String authCode = decodeInput(pRequest.getParameter("authCode"));
            String password = decodeInput(pRequest.getParameter("password"));
            if (cellPhone == null || ValidationUtils.isValidPhoneNumber(cellPhone)) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_PHONE);
                return output(responseBean);
            }
            BaseProfile user = getMyAccountManager().findUser(cellPhone);
            if (user == null) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
                return output(responseBean);
            }
            if (!getProfileManager().verifyIdentityCode(user.getPhone(), authCode)) {
                responseBean.addBusinessMsg(IdentityCodeManager.ERROR_INVALID_IDENTITY_CODE);
                return output(responseBean);
            }
            if (!(getMyAccountManager().resetPassword(user, password) > 0)) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_RESET_PASSWORD_FAILED);
                return output(responseBean);
            }
        } catch (IdentityCodeException e) {
            responseBean.addBusinessMsg(e.getMessage());
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
            pResponseBean.addBusinessMsg(MyAccountManager.ERROR_PHONE_FILED_IS_EMPTY);
            return;
        }
        String authCode = pRequest.getParameter("authCode");
        if (StringUtils.isBlank(authCode)) {
            pResponseBean.addBusinessMsg(IdentityCodeManager.ERROR_INVALID_EMPTY_AUTHO_CODE);
            return;
        }
        String password = pRequest.getParameter("password");
        if (StringUtils.isBlank(password)) {
            pResponseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_EMTPY_PASSWORD);
            return;
        }
    }

    @RequestMapping(value = "/requestAlterPwd" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestAlterPwd(HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean();
        preRequestAlterPwd(pRequest, responseBean);
        if (!responseBean.isSuccess()) {
            return output(responseBean);
        }
        TransactionStatus txStatus = ensureTransaction();

        try {
            String userId = decodeInput(pRequest.getParameter("userId"));
            String oldPassword = decodeInput(pRequest.getParameter("oldPassword"));
            String newPassword = decodeInput(pRequest.getParameter("newPassword"));
            BaseProfile user = getMyAccountManager().findUserById(userId);
            if (user == null) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
                return output(responseBean);
            }

            if (!user.getPassword().equals(oldPassword)) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_OLD_PASSWORD_INVALID);
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
            pResponseBean.addBusinessMsg("User id is empty.");
            return;
        }
        String oldPassword = pRequest.getParameter("oldPassword");
        if (StringUtils.isBlank(oldPassword)) {
            pResponseBean.addBusinessMsg(MyAccountManager.ERROR_INVALID_EMTPY_PASSWORD);
            return;
        }
        String newPassword = pRequest.getParameter("newPassword");
        if (StringUtils.isBlank(newPassword)) {
            pResponseBean.addBusinessMsg(MyAccountManager.ERROR_NEW_PASSWORD_IS_MEPTY);
            return;
        }
    }

    @RequestMapping(value = "/requestGetRelateInfo" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestGetRelateInfo(HttpServletRequest pRequest, final HttpServletResponse pResponse)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean();
        String parentId = decodeInput(pRequest.getParameter("parentId"));
        if (StringUtils.isBlank(parentId)) {
            responseBean.addBusinessMsg(ERROR_NULL_PARENT_ID);
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
            responseBean.addData("studentBirth", DateUtils.getStudentBirthString(student.getBirth()));
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
    public String requestParentAudit(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String teacherId, final
    String[] parentIds, String setPass) {
        ResponseBean responseBean = new ResponseBean();
        if (ArrayUtils.isEmpty(parentIds)) {
            responseBean.addErrorMsg(ERROR_NULL_PARENT_IDS);
            return output(responseBean);
        }
        TransactionStatus status = ensureTransaction();
        try {
            LOG.debug("requestParentAudit():parentIds:" + parentIds);
            LOG.debug("requestParentAudit():teacherId:" + teacherId);
            LOG.debug("requestParentAudit():setPass:" + setPass);

            LOG.debug("requestParentAudit():parentIds:" + parentIds);
            int[] ids = decodeInputIds(parentIds);
            LOG.debug("requestParentAudit():decode parentIds:" + ids);
            int tid = Integer.valueOf(decodeInput(teacherId));
            LOG.debug("requestParentAudit():decode teacherId:" + tid);
            int pass = Integer.valueOf(decodeInput(setPass));
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
    public String requestParentAuditAll(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String teacherId, final
    String allPass) {
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus status = ensureTransaction();
        try {
            int tid = Integer.valueOf(decodeInput(teacherId));
            int pass = Integer.valueOf(decodeInput(allPass));
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
    public String requestPendingAuditParents(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String teacherId)
            throws IOException {
        ResponseBean responseBean = new ResponseBean();
        int tid = Integer.valueOf(decodeInput(teacherId));
        List<Map<String, Object>> pendingAuditParents = getMyAccountManager().getPendingAuditParents(tid);
        responseBean.addData("parentsAuditList", pendingAuditParents);
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
}
