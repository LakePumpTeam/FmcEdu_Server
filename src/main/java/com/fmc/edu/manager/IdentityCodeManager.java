package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.service.impl.IdentityCodeService;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Yu on 2015/5/15.
 */
@Service("identityCodeManager")
public class IdentityCodeManager {
    public static final String INVALID_IDENTITY_CODE = "验证码错误.";

    public static final String EXPIRED_IDENTITY_CODE = "验证码过期.";

    public static final String UNKOWN_IDENTITY_CODE = "验证码错误.";

    @Resource(name = "identityCodeService")
    private IdentityCodeService mIdentityCodeService;

    public int insertIdentityCode(IdentityCode pIdentityCode) {
        return getIdentityCodeService().insertIdentityCode(pIdentityCode);
    }

    public IdentityCode queryIdentityCodeByPhone(String pPhone) {
        return getIdentityCodeService().queryIdentityCodeByPhone(pPhone);
    }

    public boolean verifyIdentityCode(String pPhone, String pAuthoCode) {
        IdentityCode identityCode = getIdentityCodeService().queryIdentityCodeByPhone(pPhone);
        if (identityCode == null) {
            return false;
        }

        return identityCode.getIdentityCode().equalsIgnoreCase(pAuthoCode);
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
