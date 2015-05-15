package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.exception.IdentityCodeException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.IMessageIdentifyService;
import com.fmc.edu.service.impl.ParentService;
import com.fmc.edu.service.impl.TempParentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 5/3/2015.
 */
@Service(value = "profileManager")
public class ProfileManager {

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

    public boolean verifyTempParentIdentifyingCode(String pPhoneNumber, final String pPassword, String pIdentifyingCode) {
        return getTempParentService().verifyTempParentAuthCode(pPhoneNumber, pPassword, pIdentifyingCode);
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
            boolean register = getParentService().registerParent(pParentStudentRelationship.getParentPhone(), pParent);
            if (register) {
                getParentService().registerParentStudentRelationship(pParentStudentRelationship);
            }
        }
    }

    public boolean verifyIdentityCode(int pProfileId, String pAuthoCode) {
        return getIdentityCodeManager().verifyIdentityCode(pProfileId, pAuthoCode);
    }

    public ParentProfile queryParentByPhone(String pParentPhone) {
        return getParentService().queryParentByPhone(pParentPhone);
    }

    public String obtainIdentityCode(String phone) throws Exception {
        BaseProfile user = getMyAccountManager().findUser(phone);
        if (user == null) {
            throw new ProfileException("用户不存在.");
        }
        String identifyCode = getMessageIdentifyService().sendIdentifyRequest(phone);
        if (StringUtils.isBlank(identifyCode)) {
            throw new IdentityCodeException("验证码获取失败.");
        }
        IdentityCode identityCodeEntity = new IdentityCode(user.getId(), identifyCode, getIdentityCodeManager().getIdentityCodeEndDate());
        if (getIdentityCodeManager().insertIdentityCode(identityCodeEntity) <= 0) {
            throw new IdentityCodeException("验证码获取失败.");
        }
        return identifyCode;
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
}
