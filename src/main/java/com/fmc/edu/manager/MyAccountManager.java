package com.fmc.edu.manager;

import com.fmc.edu.exception.LoginException;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.service.impl.MyAccountService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service("myAccountManager")
public class MyAccountManager {
    private static final Logger LOG = Logger.getLogger(MyAccountManager.class);

    public static final String ERROR_NOT_FIND_USER = "账号不存在.";

    public static final String ERROR_ACCOUNT_UNAVAILABLE = "账号不可用.";

    public static final String ERROR_PASSWORD_IS_INVALID = "密码错误.";

    public static final String ERROR_INVALID_PHONE = "电话号码错误.";

    public static final String ERROR_INVALID_EMTPY_PASSWORD = "密码为空.";

    public static final String ERROR_INVALID_EMPTY_SALT = "salt 为空.";

    public static final String ERROR_RESET_PASSWORD_FAILED = "密码重置失败.";

    public static final String ERROR_PHONE_FILED_IS_EMPTY = "电话号码不能为空.";

    public static final String ERROR_OLD_PASSWORD_INVALID = "旧密码不正确.";

    public static final String ERROR_NEW_PASSWORD_IS_MEPTY = "新密码不能为空.";

    @Resource(name = "myAccountService")
    private MyAccountService mMyAccountService;

    public BaseProfile loginUser(final String pAccount, final String pPassword) throws LoginException, UnsupportedEncodingException {
        BaseProfile user = getMyAccountService().findUser(pAccount);
        if (user == null) {
            throw new LoginException(ERROR_NOT_FIND_USER);
        }
        if (!user.isAvailable()) {
            throw new LoginException(ERROR_ACCOUNT_UNAVAILABLE);
        }
        if (StringUtils.isBlank(pPassword) || !pPassword.equals(user.getPassword())) {
            throw new LoginException(ERROR_PASSWORD_IS_INVALID);
        }

        user.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
        user.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        if (!getMyAccountService().saveLoginStatus(user)) {
            LOG.error("Update login status failed.");
        }
        return user;
    }

    public boolean deleteProfile(int userId) {
        return getMyAccountService().deleteProfile(userId);
    }

    public boolean parentBoundStudent(int parentId) {
        return !CollectionUtils.isEmpty(getMyAccountService().queryStudentParentRelationByParentId(parentId));
    }

    public BaseProfile findUser(final String pAccount) {
        return getMyAccountService().findUser(pAccount);
    }

    public BaseProfile findUserById(String pProfileId) {
        return getMyAccountService().findUserById(pProfileId);
    }

    public boolean updateParentAuditStatus(final int pTeacherId, final int[] pParentIds, final int pPass) {
        return getMyAccountService().updateParentAuditStatus(pTeacherId, pParentIds, pPass);
    }

    public boolean updateAllParentAuditStatus(final int pTeacherId, final int pPass) {
        return getMyAccountService().updateAllParentAuditStatus(pTeacherId, pPass);
    }

    public int resetPassword(BaseProfile pUser, String pPassword) {
        pUser.setPassword(pPassword);
        pUser.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        return getMyAccountService().resetPassword(pUser);
    }

    public List<Map<String, Object>> getPendingAuditParents(final int pTeacherId) {
        return getMyAccountService().getPendingAuditParents(pTeacherId);
    }

    public boolean addLikeNewsRelation(final int pProfileId, final int pNewsId) {
        return getMyAccountService().addLikeNewsRelation(pProfileId, pNewsId);
    }

    public boolean deleteLikeNewsRelation(int pProfileId, int pNewsId) {
        return getMyAccountService().deleteLikeNewsRelation(pProfileId, pNewsId);
    }

    public boolean updateBaseProfile(BaseProfile pBaseProfile) {
        return getMyAccountService().updateBaseProfile(pBaseProfile);
    }

    public Map<String, Object> queryLikeNewsRelation(int pProfileId, int pNewsId) {
        return getMyAccountService().queryLikeNewsRelation(pProfileId, pNewsId);
    }

    public MyAccountService getMyAccountService() {
        return mMyAccountService;
    }

    public void setMyAccountService(MyAccountService pMyAccountService) {
        mMyAccountService = pMyAccountService;
    }
}
