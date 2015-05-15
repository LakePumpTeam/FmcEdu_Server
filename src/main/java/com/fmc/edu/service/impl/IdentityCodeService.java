package com.fmc.edu.service.impl;

import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.repository.IIdentityCodeRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Yu on 2015/5/15.
 */
@Service("identityCodeService")
public class IdentityCodeService {
    private IIdentityCodeRepository mIdentityCodeRepository;

    public int insertIdentityCode(IdentityCode pIdentityCode) {
        return getIdentityCodeRepository().insertIdentityCode(pIdentityCode);
    }

    public IdentityCode queryIdentityCodeByProfileId(int pProfileId) {
        return getIdentityCodeRepository().queryIdentityCodeByProfileId(pProfileId);
    }

    public IIdentityCodeRepository getIdentityCodeRepository() {
        return mIdentityCodeRepository;
    }

    public void setIdentityCodeRepository(IIdentityCodeRepository pIdentityCodeRepository) {
        this.mIdentityCodeRepository = pIdentityCodeRepository;
    }
}
