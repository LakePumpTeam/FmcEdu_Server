package com.fmc.edu.repository;

import com.fmc.edu.model.profile.BaseProfile;

/**
 * Created by Yu on 5/12/2015.
 */
public interface IMyAccountRepository {
    String QUER_USER_BY_PHONE = "com.fmc.edu.myaccount.login";

    BaseProfile findUser(final String pPhone);

    String UPDATE_LOGIN_STATUS = "com.fmc.edu.myaccount.updateLoginStatus";

    int saveLoginStatus(final BaseProfile pLoginedUser);

}
