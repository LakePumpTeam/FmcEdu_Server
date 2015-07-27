package com.fmc.edu.model.push;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 2015/7/26.
 */
public class PushMessage extends BaseBean {

    private int mProfileId;
    private String mTitle;
    private String mContent;
    //1: push to all device  2: push to single device
    private int mPushType;
    private int mPushDeviceType;
    private boolean mPushStatus;
    private Timestamp mCreationDate;

    public int getProfileId() {
        return mProfileId;
    }

    public void setProfileId(int pProfileId) {
        mProfileId = pProfileId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String pContent) {
        mContent = pContent;
    }

    public int getPushType() {
        return mPushType;
    }

    public void setPushType(int pPushType) {
        mPushType = pPushType;
    }

    public int getPushDeviceType() {
        return mPushDeviceType;
    }

    public void setPushDeviceType(int pPushDeviceType) {
        mPushDeviceType = pPushDeviceType;
    }

    public boolean isPushStatus() {
        return mPushStatus;
    }

    public void setPushStatus(boolean pPushStatus) {
        mPushStatus = pPushStatus;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }
}
