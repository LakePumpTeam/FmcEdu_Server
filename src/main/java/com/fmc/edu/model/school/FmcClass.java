package com.fmc.edu.model.school;

import com.fmc.edu.model.BaseBean;

/**
 * Created by Yu on 5/12/2015.
 */
public class FmcClass extends BaseBean {

	private int mSchoolId;

	private String mSchoolName;

	private String mGrade;

	private boolean mAvailable;

	private String mRealClass;

	private int mHeadTeacherId;

	private String mHeadTeacherName;

	private int mStudentCount;

	public FmcClass() {
	}

	public String getSchoolName() {
		return mSchoolName;
	}

	public void setSchoolName(final String pSchoolName) {
		mSchoolName = pSchoolName;
	}

	public int getSchoolId() {
		return mSchoolId;
	}

	public void setSchoolId(int pSchoolId) {
		mSchoolId = pSchoolId;
	}

	public String getGrade() {
		return mGrade;
	}

	public void setGrade(String pGrade) {
		mGrade = pGrade;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public String getRealClass() {
		return mRealClass;
	}

	public void setRealClass(String pRealClass) {
		mRealClass = pRealClass;
	}

	public int getHeadTeacherId() {
		return mHeadTeacherId;
	}

	public void setHeadTeacherId(int pHeadTeacherId) {
		mHeadTeacherId = pHeadTeacherId;
	}

	public String getHeadTeacherName() {
		return mHeadTeacherName;
	}

	public void setHeadTeacherName(final String pHeadTeacherName) {
		mHeadTeacherName = pHeadTeacherName;
	}

	public int getStudentCount() {
		return mStudentCount;
	}

	public void setStudentCount(final int pStudentCount) {
		mStudentCount = pStudentCount;
	}
}
