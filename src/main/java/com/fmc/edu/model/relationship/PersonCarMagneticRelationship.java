package com.fmc.edu.model.relationship;

/**
 * Created by Yu on 7/18/2015.
 */
public class PersonCarMagneticRelationship extends BaseRelationship {
    private int mStudentId;
    private int mParentId;
    private int mMagneticCardId;
    private int mAvailable;
    private boolean mApproved;

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int pStudentId) {
        mStudentId = pStudentId;
    }

    public int getParentId() {
        return mParentId;
    }

    public void setParentId(int pParentId) {
        mParentId = pParentId;
    }

    public int getMagneticCardId() {
        return mMagneticCardId;
    }

    public void setMagneticCardId(int pMagneticCardId) {
        mMagneticCardId = pMagneticCardId;
    }

    public int isAvailable() {
        return mAvailable;
    }

    public void setAvailable(int pAvailable) {
        mAvailable = pAvailable;
    }

    public boolean isApproved() {
        return mApproved;
    }

    public void setApproved(boolean pApproved) {
        mApproved = pApproved;
    }
}
