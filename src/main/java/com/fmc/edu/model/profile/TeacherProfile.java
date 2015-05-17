package com.fmc.edu.model.profile;

import com.fmc.edu.model.school.School;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yu on 5/12/2015.
 */
public class TeacherProfile extends BaseProfile implements Serializable {

	private School mSchool;

	private boolean mHeadTeacher;

	private boolean mInitialized;

	private boolean mMale;

	private String mResume;

	private String mCourse;

	private Date mBirth;

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

	public String getResume() {
		return mResume;
	}

	public void setResume(final String pResume) {
		mResume = pResume;
	}

	public String getCourse() {
		return mCourse;
	}

	public void setCourse(final String pCourse) {
		mCourse = pCourse;
	}

	public Date getBirth() {
		return mBirth;
	}

	public void setBirth(final Date pBirth) {
		mBirth = pBirth;
	}
}
