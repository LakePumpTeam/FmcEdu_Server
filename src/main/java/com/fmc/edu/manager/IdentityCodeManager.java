package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.service.impl.IdentityCodeService;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Yu on 2015/5/15.
 */
@Service("identityCodeManager")
public class IdentityCodeManager {
    private IdentityCodeService mIdentityCodeService;

    public int insertIdentityCode(IdentityCode pIdentityCode) {
        return getIdentityCodeService().insertIdentityCode(pIdentityCode);
    }

    public IdentityCode queryIdentityCodeByProfileId(int pProfileId) {
        return getIdentityCodeService().queryIdentityCodeByProfileId(pProfileId);
    }

    public boolean verifyIdentityCode(int pProfileId, String pAuthoCode) {
        IdentityCode identityCode = getIdentityCodeService().queryIdentityCodeByProfileId(pProfileId);
        if (identityCode == null) {
            return false;
        }

        return identityCode.getmIdentityCode().equalsIgnoreCase(pAuthoCode);
    }

    public Date getIdentityCodeEndDate() {
        return DateUtils.addSeconds(WebConfig.getIdentifyCodeSurvivalPeriod(), new Date());
    }

    public IdentityCodeService getIdentityCodeService() {
        return mIdentityCodeService;
    }

    public void setIdentityCodeService(IdentityCodeService pIdentityCodeService) {
        this.mIdentityCodeService = pIdentityCodeService;
    }
}
