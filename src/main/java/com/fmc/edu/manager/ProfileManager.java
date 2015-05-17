package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.exception.IdentityCodeException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.IMessageIdentifyService;
import com.fmc.edu.service.impl.ParentService;
import com.fmc.edu.service.impl.TempParentService;
import com.fmc.edu.util.RepositoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 5/3/2015.
 */
@Service(value = "profileManager")
public class ProfileManager {
    private static final Logger LOG = Logger.getLogger(ProfileManager.class);

    @Resource(name = "dummyMessageIdentifyService")
    private IMessageIdentifyService mDummyMessageIdentifyService;

    @Resource(name = "messageIdentifyService")
    private IMessageIdentifyService mMessageIdentifyService;

    @Resource(name = "tempParentService")
    private TempParentService mTempParentService;

    @Resource(name = "parentService")
    private ParentService mParentService;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "identityCodeManager")
    private IdentityCodeManager mIdentityCodeManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    public boolean verifyTempParentIdentifyingCode(String pPhoneNumber, final String pPassword, String pIdentifyingCode) {
        return getTempParentService().verifyTempParentAuthCode(pPhoneNumber, pPassword, pIdentifyingCode);
    }

    public boolean verifyIdentityCodeAndRegister(String pPhoneNumber, final String pPassword, String pIdentifyingCode) throws IdentityCodeException, ProfileException {
        if (getIdentityCodeManager().verifyIdentityCode(pPhoneNumber, pIdentifyingCode)) {
            BaseProfile user = getMyAccountManager().findUser(pPhoneNumber);
            if (user != null) {
                if (user.getProfileType() == ProfileType.PARENT.getValue()) {
                    if (!getMyAccountManager().parentBoundStudent(user.getId())) {
                        LOG.debug("Found parent user which not bound any student, so delete this user to allow re-register.");
                        getMyAccountManager().deleteProfile(user.getId());
                    }
                } else if (user.getProfileType() == ProfileType.TEACHER.getValue()) {
                    //FIXME add specially logic for teacher user in the future.
                    throw new ProfileException("账号已存在.");
                } else {
                    throw new ProfileException("账号已存在.");
                }
            }
            BaseProfile baseProfile = new BaseProfile();
            baseProfile.setPhone(pPhoneNumber);
            baseProfile.setPassword(pPassword);
            baseProfile.setProfileType(ProfileType.PARENT.getValue());
            if (getParentService().initialProfile(baseProfile)) {
                return true;
            }
        } else {
            throw new IdentityCodeException("验证码无效.");
        }
        return false;
    }

    public boolean registerParentAddress(String pPhoneNumber, Address pAddress) {
        return getParentService().registerParentAddress(pPhoneNumber, pAddress);
    }

    public String registerTempParent(String pPhoneNumber) {
        String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
        boolean persistentFailure = getTempParentService().registerTempParent(pPhoneNumber, identifyCode);
        return identifyCode;
    }

    public void registerRelationshipBetweenStudent(final ParentStudentRelationship pParentStudentRelationship, final Student pStudent, final ParentProfile pParent) {
        boolean persist = getSchoolManager().persistStudent(pStudent);
        if (persist && pStudent.getId() > 0) {
            pParentStudentRelationship.setStudentId(pStudent.getId());
            boolean register = false;
            if (RepositoryUtils.isItemExist(pParent)) {
                register = getParentService().updateParentProfile(pParentStudentRelationship.getParentPhone(), pParent);
            } else {
                BaseProfile baseProfile = getMyAccountManager().findUser(pParentStudentRelationship.getParentPhone());
                pParent.setId(baseProfile.getId());
                register = getParentService().insertParentProfile(pParent);
                getParentService().updateProfileName(pParent);
            }
            if (register) {
                getParentService().registerParentStudentRelationship(pParentStudentRelationship);
            }
        }
    }

    public boolean verifyIdentityCode(String pPhone, String pAuthoCode) {
        return getIdentityCodeManager().verifyIdentityCode(pPhone, pAuthoCode);
    }

    public ParentProfile queryParentByPhone(String pParentPhone) {
        return getParentService().queryParentByPhone(pParentPhone);
    }

    public String obtainIdentityCode(String phone) throws Exception {
        String identifyCode = getMessageIdentifyService().sendIdentifyRequest(phone);
        if (StringUtils.isBlank(identifyCode)) {
            throw new IdentityCodeException("验证码获取失败.");
        }
        IdentityCode identityCodeEntity = new IdentityCode(phone, identifyCode);
        if (getIdentityCodeManager().insertIdentityCode(identityCodeEntity) <= 0) {
            throw new IdentityCodeException("验证码获取失败.");
        }
        return identifyCode;
    }

    public ParentProfile queryParentDetailById(final int pParentId) {
        return getParentService().getParentRepository().queryParentDetailById(pParentId);
    }

    /**
     * Return the MessageIdentifyService according the develop status.
     *
     * @return
     */
    public IMessageIdentifyService getMessageIdentifyService() {
        if (WebConfig.isDevelopment()) {
            return mDummyMessageIdentifyService;
        }
        return mMessageIdentifyService;
    }

    public void setMessageIdentifyService(IMessageIdentifyService pMessageIdentifyService) {
        mMessageIdentifyService = pMessageIdentifyService;
    }

    public void setDummyMessageIdentifyService(IMessageIdentifyService pDummyMessageIdentifyService) {
        mDummyMessageIdentifyService = pDummyMessageIdentifyService;
    }

    public TempParentService getTempParentService() {
        return mTempParentService;
    }

    public void setTempParentService(final TempParentService pTempParentService) {
        mTempParentService = pTempParentService;
    }

    public ParentService getParentService() {
        return mParentService;
    }

    public void setParentService(final ParentService pParentService) {
        mParentService = pParentService;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(final SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }

    public IdentityCodeManager getIdentityCodeManager() {
        return mIdentityCodeManager;
    }

    public void setIdentityCodeManager(IdentityCodeManager pIdentityCodeManager) {
        this.mIdentityCodeManager = pIdentityCodeManager;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        this.mMyAccountManager = pMyAccountManager;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(TeacherManager pTeacherManager) {
        this.mTeacherManager = pTeacherManager;
    }
}
