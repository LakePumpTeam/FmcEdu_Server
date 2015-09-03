package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.exception.IdentityCodeException;
import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.service.impl.IdentityCodeService;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Yu on 2015/5/15.
 */
@Service("identityCodeManager")
public class IdentityCodeManager {
    @Resource(name = "identityCodeService")
    private IdentityCodeService mIdentityCodeService;

    public int insertIdentityCode(IdentityCode pIdentityCode) {
        return getIdentityCodeService().insertIdentityCode(pIdentityCode);
    }

    public IdentityCode queryIdentityCodeByPhone(String pPhone) {
        return getIdentityCodeService().queryIdentityCodeByPhone(pPhone);
    }

    public boolean verifyIdentityCode(String pPhone, String pAuthCode) throws IdentityCodeException {
        IdentityCode identityCode = getIdentityCodeService().queryIdentityCodeByPhone(pPhone);
        if (identityCode == null) {
            throw new IdentityCodeException(ResourceManager.ERROR_IDENTITY_CODE_INVALID);
        }
        if (identityCode.getIdentityEndDate().before(new Timestamp(System.currentTimeMillis()))) {
            throw new IdentityCodeException(ResourceManager.ERROR_IDENTITY_CODE_EXPIRED);
        }
        return identityCode.getIdentityCode().equalsIgnoreCase(pAuthCode);
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
