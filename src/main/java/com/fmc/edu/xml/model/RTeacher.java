package com.fmc.edu.xml.model;

/**
 * Created by dylanyu on 9/8/2015.
 */
public class RTeacher {
    private String mGrade;
    private String mCls;
    private String mName;
    private String mPhone;
    private String mSex;
    private String mCourse;
    private String mBirthday;
    private String mResume;
    private boolean mMasterTeacher;

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String pGrade) {
        mGrade = pGrade;
    }

    public String getCls() {
        return mCls;
    }

    public void setCls(String pCls) {
        mCls = pCls;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String pPhone) {
        mPhone = pPhone;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String pSex) {
        mSex = pSex;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String pCourse) {
        mCourse = pCourse;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String pBirthday) {
        mBirthday = pBirthday;
    }

    public String getResume() {
        return mResume;
    }

    public void setResume(String pResume) {
        mResume = pResume;
    }

    public boolean isMasterTeacher() {
        return mMasterTeacher;
    }

    public void setMasterTeacher(boolean pMasterTeacher) {
        mMasterTeacher = pMasterTeacher;
    }
}
