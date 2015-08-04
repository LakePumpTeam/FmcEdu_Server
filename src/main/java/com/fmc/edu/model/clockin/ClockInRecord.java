package com.fmc.edu.model.clockin;

import com.fmc.edu.model.BaseBean;

import java.util.Date;

/**
 * Created by Yu on 7/18/2015.
 */
public class ClockInRecord extends BaseBean {
    private int mMagneticCardId;
    private int mType;
    private int mAttendanceFlag;
    private Date mAttendanceDate;
    private Date mCreationDate;
    private int mClockInPersonId;
    private String mClockInPersonName;

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

    public Date getAttendanceDate() {
        return mAttendanceDate;
    }

    public void setAttendanceDate(Date pAttendanceDate) {
        mAttendanceDate = pAttendanceDate;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date pCreationDate) {
        mCreationDate = pCreationDate;
    }

    public int getClockInPersonId() {
        return mClockInPersonId;
    }

    public void setClockInPersonId(int pClockInPersonId) {
        mClockInPersonId = pClockInPersonId;
    }

    public String getClockInPersonName() {
        return mClockInPersonName;
    }

    public void setClockInPersonName(String pClockInPersonName) {
        mClockInPersonName = pClockInPersonName;
    }
}
