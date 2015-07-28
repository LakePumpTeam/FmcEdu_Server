package com.fmc.edu.model.app;

import com.fmc.edu.model.BaseBean;

/**
 * Created by Yu on 2015/7/25.
 */
public class AppSetting extends BaseBean {
    private boolean mIsBel;
    private boolean mIsVibra;
    private int mProfileId;

    public boolean isIsBel() {
        return mIsBel;
    }

    public void setIsBel(boolean pIsBel) {
        mIsBel = pIsBel;
    }

    public boolean isIsVibra() {
        return mIsVibra;
    }

    public void setIsVibra(boolean pIsVibra) {
        mIsVibra = pIsVibra;
    }

    public int getProfileId() {
        return mProfileId;
    }

    public void setProfileId(int pProfileId) {
        mProfileId = pProfileId;
    }
}
