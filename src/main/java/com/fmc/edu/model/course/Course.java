package com.fmc.edu.model.course;

import com.fmc.edu.model.BaseBean;

import java.sql.Time;

/**
 * Created by Yu on 2015/5/21.
 */
public class Course extends BaseBean {
    private int mTimeTableId;
    private String mCourseName;
    private Integer mOrder;
    private String mOrderName;
    private Time mStartTime;
    private Time mEndTime;
    private Integer mWeek;
    private Boolean mAvailable;

    public int getTimeTableId() {
        return mTimeTableId;
    }

    public void setTimeTableId(int pTimeTableId) {
        mTimeTableId = pTimeTableId;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String pCourseName) {
        mCourseName = pCourseName;
    }

    public Integer getOrder() {
        return mOrder;
    }

    public void setOrder(Integer pOrder) {
        mOrder = pOrder;
    }

    public String getOrderName() {
        return mOrderName;
    }

    public void setOrderName(String pOrderName) {
        mOrderName = pOrderName;
    }

    public Time getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Time pStartTime) {
        mStartTime = pStartTime;
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Time pEndTime) {
        mEndTime = pEndTime;
    }

    public Integer getWeek() {
        return mWeek;
    }

    public void setWeek(Integer pWeek) {
        mWeek = pWeek;
    }

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean pAvailable) {
        mAvailable = pAvailable;
    }
}
