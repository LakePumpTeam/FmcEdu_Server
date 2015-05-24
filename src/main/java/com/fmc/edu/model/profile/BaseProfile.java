package com.fmc.edu.model.profile;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/5/2015.
 */
public class BaseProfile extends BaseBean {

    private String mName;

    private String mPhone;

    private String mPassword;

    private String mAppId;

    private String mChannelId;

    private String mSalt;

    private Timestamp mCreationDate;

    private Timestamp mLastLoginDate;

    private Boolean mAvailable;

    private Integer mProfileType;

    private int mLastPCId;

    private int mLastCLId;

    private int mLastSCId;

    private int mLastSDActivity;

    private int mLastSDNews;

    private int mLastSDNotify;

    private int mLastPCDId;

    public String getName() {
        return mName;
    }

    public void setName(final String pName) {
        mName = pName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(final String pPhone) {
        mPhone = pPhone;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(final String pPassword) {
        mPassword = pPassword;
    }

    public String getSalt() {
        return mSalt;
    }

    public void setSalt(String pSalt) {
        mSalt = pSalt;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(String pChannelId) {
        mChannelId = pChannelId;
    }

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(final String pAppId) {
        mAppId = pAppId;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(final Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }

    public Timestamp getLastLoginDate() {
        return mLastLoginDate;
    }

    public void setLastLoginDate(final Timestamp pLastLoginDate) {
        mLastLoginDate = pLastLoginDate;
    }

    public Boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(final Boolean pAvailable) {
        mAvailable = pAvailable;
    }

    public Integer getProfileType() {
        return mProfileType;
    }

    public void setProfileType(final Integer pProfileType) {
        mProfileType = pProfileType;
    }

    public int getLastCLId() {
        return mLastCLId;
    }

    public void setLastCLId(int pLastCLId) {
        mLastCLId = pLastCLId;
    }

    public int getLastPCId() {
        return mLastPCId;
    }

    public void setLastPCId(int pLastPCId) {
        mLastPCId = pLastPCId;
    }

    public int getLastSCId() {
        return mLastSCId;
    }

    public void setLastSCId(int pLastSCId) {
        mLastSCId = pLastSCId;
    }

    public int getLastSDActivity() {
        return mLastSDActivity;
    }

    public void setLastSDActivity(int pLastSDActivity) {
        mLastSDActivity = pLastSDActivity;
    }

    public int getLastSDNews() {
        return mLastSDNews;
    }

    public void setLastSDNews(int pLastSDNews) {
        mLastSDNews = pLastSDNews;
    }

    public int getLastSDNotify() {
        return mLastSDNotify;
    }

    public void setLastSDNotify(int pLastSDNotify) {
        mLastSDNotify = pLastSDNotify;
    }

    public int getLastPCDId() {
        return mLastPCDId;
    }

    public void setLastPCDId(int pLastPCDId) {
        mLastPCDId = pLastPCDId;
    }
}
