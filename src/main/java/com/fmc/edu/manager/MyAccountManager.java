package com.fmc.edu.manager;

import com.fmc.edu.exception.LoginException;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.service.impl.MyAccountService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Component("myAccountManager")
public class MyAccountManager {
    private static final Logger LOG = Logger.getLogger(MyAccountManager.class);

    public static final String NOT_FIND_USER = "账号不存在.";

    public static final String ACCOUNT_UNAVAILABLE = "账号不可用.";

    public static final String PASSWORD_IS_INVALID = "密码错误.";

    public static final String ERROR_INVALID_PHONE = "电话号码错误.";

    public static final String ERROR_INVALID_EMPTY_AUTHO_CODE = "验证码为空.";

    public static final String ERROR_INVALID_EMTPY_PASSWORD = "密码为空.";

    public static final String ERROR_INVALID_EMPTY_SALT = "salt 为空.";
    @Resource(name = "myAccountService")
    private MyAccountService mMyAccountService;

    public BaseProfile loginUser(final String pAccount, final String pPassword) throws LoginException, UnsupportedEncodingException {
        BaseProfile user = getMyAccountService().findUser(pAccount);
        if (user == null) {
            throw new LoginException(NOT_FIND_USER);
        }
        if (!user.isAvailable()) {
            throw new LoginException(ACCOUNT_UNAVAILABLE);
        }
        if (StringUtils.isBlank(pPassword) || !pPassword.equals(user.getPassword())) {
            throw new LoginException(PASSWORD_IS_INVALID);
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

    public MyAccountService getMyAccountService() {
        return mMyAccountService;
    }

    public void setMyAccountService(MyAccountService pMyAccountService) {
        mMyAccountService = pMyAccountService;
    }
}
