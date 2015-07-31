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

    private boolean mOnline;

    private int mLastPCId;

    private int mLastSDATId;

    private int mLastSDNFId;

    private int mLastSDNWId;

    private int mLastCLId;

    private int mLastPCEId;

    private int mLastBBSId;

    private int mDeviceType;

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

    public int getLastSDATId() {
        return mLastSDATId;
    }

    public void setLastSDATId(int pLastSDATId) {
        mLastSDATId = pLastSDATId;
    }

    public int getLastSDNFId() {
        return mLastSDNFId;
    }

    public void setLastSDNFId(int pLastSDNFId) {
        mLastSDNFId = pLastSDNFId;
    }

    public int getLastSDNWId() {
        return mLastSDNWId;
    }

    public void setLastSDNWId(int pLastSDNWId) {
        mLastSDNWId = pLastSDNWId;
    }

    public int getLastPCEId() {
        return mLastPCEId;
    }

    public void setLastPCEId(int pLastPCEId) {
        mLastPCEId = pLastPCEId;
    }

    public int getLastBBSId() {
        return mLastBBSId;
    }

    public void setLastBBSId(int pLastBBSId) {
        mLastBBSId = pLastBBSId;
    }

    public int getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(int pDeviceType) {
        mDeviceType = pDeviceType;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void setOnline(boolean pOnline) {
        mOnline = pOnline;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseProfile)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getId() == ((BaseProfile) obj).getId()) {
            return true;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(getId()).hashCode();
    }
}
