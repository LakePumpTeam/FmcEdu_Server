package com.fmc.edu.model.profile;

import com.fmc.edu.model.school.School;

/**
 * Created by Yu on 5/12/2015.
 */
public class TeacherProfile extends BaseProfile {

    private School mSchool;
    private boolean mHeadTeacher;
    private boolean mInitialized;
    private boolean mMale;

    public School getSchool() {
        return mSchool;
    }

    public void setSchool(School pSchool) {
        mSchool = pSchool;
    }

    public boolean isHeadTeacher() {
        return mHeadTeacher;
    }

    public void setHeadTeacher(boolean pHeadTeacher) {
        mHeadTeacher = pHeadTeacher;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void setInitialized(boolean pInitialized) {
        mInitialized = pInitialized;
    }

    public boolean isMale() {
        return mMale;
    }

    public void setMale(boolean pMale) {
        this.mMale = pMale;
    }
}
