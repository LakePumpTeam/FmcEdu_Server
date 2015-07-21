package com.fmc.edu.model.clockin;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 7/18/2015.
 */
public class ClockInRecord extends BaseBean {
    private int mMagneticCardId;
    private int mType;
    private int mAttendanceFlag;
    private Timestamp mAttendanceDate;
    private Timestamp mCreationDate;
    private int mClockInPersionId;

    public int getMagneticCardId() {
        return mMagneticCardId;
    }

    public void setMagneticCardId(int pMagneticCardId) {
        mMagneticCardId = pMagneticCardId;
    }

    public int getType() {
        return mType;
    }

    public void setType(int pType) {
        mType = pType;
    }

    public int getAttendanceFlag() {
        return mAttendanceFlag;
    }

    public void setAttendanceFlag(int pAttendanceFlag) {
        mAttendanceFlag = pAttendanceFlag;
    }

    public Timestamp getAttendanceDate() {
        return mAttendanceDate;
    }

    public void setAttendanceDate(Timestamp pAttendanceDate) {
        mAttendanceDate = pAttendanceDate;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }

    public int getClockInPersionId() {
        return mClockInPersionId;
    }

    public void setClockInPersionId(int pClockInPersionId) {
        mClockInPersionId = pClockInPersionId;
    }
}
