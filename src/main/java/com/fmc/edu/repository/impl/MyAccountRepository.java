package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IMyAccountRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository("myAccountRepository")
public class MyAccountRepository extends BaseRepository implements IMyAccountRepository {
    @Override
    public BaseProfile findUser(final String pPhone) {
        return getSqlSession().selectOne(QUER_USER_BY_PHONE, pPhone);
    }

    @Override
    public int saveLoginStatus(final BaseProfile pLoginedUser) {
        return getSqlSession().update(UPDATE_LOGIN_STATUS, pLoginedUser);
    }
}
