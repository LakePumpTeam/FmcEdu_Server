package com.fmc.edu.manager;

import com.fmc.edu.exception.LoginException;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.service.impl.MyAccountService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * Created by Yu on 5/12/2015.
 */
@Component("myAccountManager")
public class MyAccountManager {
    private static final Logger LOG = Logger.getLogger(MyAccountManager.class);

    protected String NOT_FIND_USER = "notFindUser";

    protected String ACCOUNT_UNAVAILABLE = "accountUnavailable";

    protected String PASSWORD_IS_INVALID = "passwordIsInvalid";

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

    public BaseProfile findUser(final String pAccount) {
        return getMyAccountService().findUser(pAccount);
    }

    public BaseProfile findUserById(String pProfileId) {
        return getMyAccountService().findUserById(pProfileId);
    }

    public int resetPassword(BaseProfile pUser, String pPassword) {
        pUser.setPassword(pPassword);
        pUser.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        return getMyAccountService().resetPassword(pUser);
    }

    public MyAccountService getMyAccountService() {
        return mMyAccountService;
    }

    public void setMyAccountService(MyAccountService pMyAccountService) {
        mMyAccountService = pMyAccountService;
    }

}