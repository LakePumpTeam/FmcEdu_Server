package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.repository.IMyAccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yu on 5/12/2015.
 */
@Service("myAccountService")
public class MyAccountService {
    @Resource(name = "myAccountRepository")
    private IMyAccountRepository mMyAccountRepository;

    public BaseProfile findUser(final String pPhone) {
        return getMyAccountRepository().findUser(pPhone);
    }

    public boolean saveLoginStatus(final BaseProfile pLoginedUser) {
        int updatedCount = getMyAccountRepository().saveLoginStatus(pLoginedUser);
        return updatedCount > 0 ? true : false;
    }

    public IMyAccountRepository getMyAccountRepository() {
        return mMyAccountRepository;
    }

    public void setMyAccountRepository(IMyAccountRepository pMyAccountRepository) {
        mMyAccountRepository = pMyAccountRepository;
    }
}
