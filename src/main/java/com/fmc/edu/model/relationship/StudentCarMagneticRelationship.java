package com.fmc.edu.model.relationship;

/**
 * Created by Yu on 7/18/2015.
 */
public class StudentCarMagneticRelationship extends BaseRelationship {
    private int mStudentId;
    private int mMagneticCardId;
    private boolean mAvailable;
    private boolean mApproved;

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int pStudentId) {
        mStudentId = pStudentId;
    }

    public int getMagneticCardId() {
        return mMagneticCardId;
    }

    public void setMagneticCardId(int pMagneticCardId) {
        mMagneticCardId = pMagneticCardId;
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean pAvailable) {
        mAvailable = pAvailable;
    }

    public boolean isApproved() {
        return mApproved;
    }

    public void setApproved(boolean pApproved) {
        mApproved = pApproved;
    }
}
