package com.fmc.edu.model.common;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.BaseBean;
import com.fmc.edu.util.DateUtils;

import java.util.Date;

/**
 * Created by Yu on 2015/5/15.
 */
public class IdentityCode extends BaseBean {
    private int mProfileId;
    private String mIdentityCode;
    private Date mIdentityEndDate;

    public IdentityCode() {
    }

    public IdentityCode(int pProfileId, String pIdentityCode) {
        this(pProfileId, pIdentityCode, DateUtils.addSeconds(WebConfig.getIdentifyCodeSurvivalPeriod(), new Date()));
    }

    public IdentityCode(int pProfileId, String pIdentityCode, Date pIdentityEndDate) {
        this.mProfileId = pProfileId;
        this.mIdentityCode = pIdentityCode;
        this.mIdentityEndDate = pIdentityEndDate;
    }


    public int getmProfileId() {
        return mProfileId;
    }

    public void setmProfileId(int mProfileId) {
        this.mProfileId = mProfileId;
    }

    public String getmIdentityCode() {
        return mIdentityCode;
    }

    public void setmIdentityCode(String mIdentityCode) {
        this.mIdentityCode = mIdentityCode;
    }
}
