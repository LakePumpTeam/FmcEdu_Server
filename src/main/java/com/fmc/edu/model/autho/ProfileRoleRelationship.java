package com.fmc.edu.model.autho;

import com.fmc.edu.model.relationship.BaseRelationship;

/**
 * Created by Yu on 2015/9/8.
 */
public class ProfileRoleRelationship extends BaseRelationship {
    private int mProfileId;
    private int mRoleId;
    private boolean mAvailable;

    public int getProfileId() {
        return mProfileId;
    }

    public void setProfileId(int pProfileId) {
        mProfileId = pProfileId;
    }

    public int getRoleId() {
        return mRoleId;
    }

    public void setRoleId(int pRoleId) {
        mRoleId = pRoleId;
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean pAvailable) {
        mAvailable = pAvailable;
    }
}
