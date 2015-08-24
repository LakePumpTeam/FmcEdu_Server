package com.fmc.edu.model.relationship;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;

/**
 * Created by Yove on 8/23/2015.
 */
public class TeacherClassRelationship extends BaseRelationship {

	private int mTeacherId;

	private int mClassId;

	private FmcClass mFmcClass;

	private TeacherProfile mTeacher;

	private boolean mAvailable;

	private String mSubTitle;

	private boolean mHeadTeacher;

	public int getTeacherId() {
		return mTeacherId;
	}

	public void setTeacherId(final int pTeacherId) {
		mTeacherId = pTeacherId;
	}

	public int getClassId() {
		return mClassId;
	}

	public void setClassId(final int pClassId) {
		mClassId = pClassId;
	}

	public FmcClass getFmcClass() {
		return mFmcClass;
	}

	public void setFmcClass(final FmcClass pFmcClass) {
		mFmcClass = pFmcClass;
	}

	public TeacherProfile getTeacher() {
		return mTeacher;
	}

	public void setTeacher(final TeacherProfile pTeacher) {
		mTeacher = pTeacher;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public String getSubTitle() {
		return mSubTitle;
	}

	public void setSubTitle(final String pSubTitle) {
		mSubTitle = pSubTitle;
	}

	public boolean isHeadTeacher() {
		return mHeadTeacher;
	}

	public void setHeadTeacher(final boolean pHeadTeacher) {
		mHeadTeacher = pHeadTeacher;
	}
}
