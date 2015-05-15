package com.fmc.edu.repository;

import com.fmc.edu.model.profile.BaseProfile;

/**
 * Created by Yu on 5/12/2015.
 */
public interface IMyAccountRepository {
    String QUER_USER_BY_PHONE = "com.fmc.edu.myaccount.login";

    BaseProfile findUser(final String pPhone);

    String QUER_USER_BY_PROFILE_ID = "com.fmc.edu.myaccount.findUserById";

    BaseProfile findUserById(final String pProfileId);

    String UPDATE_LOGIN_STATUS = "com.fmc.edu.myaccount.updateLoginStatus";

    int saveLoginStatus(final BaseProfile pLoginedUser);

    String RESET_PASSWORD = "com.fmc.edu.myaccount.resetPassword";

    int resetPassword(final BaseProfile pLoginedUser);

}
