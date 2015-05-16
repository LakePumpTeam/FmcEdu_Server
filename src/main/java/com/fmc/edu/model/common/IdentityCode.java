package com.fmc.edu.model.common;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.BaseBean;
import com.fmc.edu.util.DateUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Yu on 2015/5/15.
 */
public class IdentityCode extends BaseBean {
    private String mPhone;
    private String mIdentityCode;
    private Timestamp mIdentityEndDate;

    public IdentityCode() {
    }

    public IdentityCode(String pPhone, String pIdentityCode) {
        this(pPhone, pIdentityCode, new Timestamp(DateUtils.addSeconds(WebConfig.getIdentifyCodeSurvivalPeriod(), new Date()).getTime()));
    }

    public IdentityCode(String pPhone, String pIdentityCode, Timestamp pIdentityEndDate) {
        this.mPhone = pPhone;
        this.mIdentityCode = pIdentityCode;
        this.mIdentityEndDate = pIdentityEndDate;
    }


    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String pPhone) {
        this.mPhone = pPhone;
    }

    public String getIdentityCode() {
        return mIdentityCode;
    }

    public void setIdentityCode(String pIdentityCode) {
        this.mIdentityCode = pIdentityCode;
    }

    public Timestamp getIdentityEndDate() {
        return mIdentityEndDate;
    }

    public void setIdentityEndDate(Timestamp pIdentityEndDate) {
        this.mIdentityEndDate = pIdentityEndDate;
    }
}
