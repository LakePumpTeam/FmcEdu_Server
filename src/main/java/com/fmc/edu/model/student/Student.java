package com.fmc.edu.model.student;

import com.fmc.edu.model.BaseBean;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.util.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Yove on 5/8/2015.
 */
public class Student extends BaseBean {

	private int mClassId;

	private String mName;

	private boolean mMale;

	private Date mBirth;

	private String mBirthStr;

	private String mRingNumber;

	private String mRingPhone;

	private Timestamp mCreationDate;

	private boolean mAvailable;

	private String mIdentifyCode;

	private ParentStudentRelationship mParentStudentRelationship;

	private School mSchool;

	private FmcClass mFmcClass;

	public Student() {
	}

	public Student(final int pClassId, final String pName) {
		mClassId = pClassId;
		mName = pName;
	}

	public void convertBirth() throws ParseException {
		mBirth = DateUtils.convertStringToDate(getBirthStr());
	}

	public int getClassId() {
		return mClassId;
	}

	public void setClassId(final int pClassId) {
		mClassId = pClassId;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String pName) {
		mName = pName;
	}

	public boolean isMale() {
		return mMale;
	}

	public void setMale(final boolean pMale) {
		mMale = pMale;
	}

	public Date getBirth() {
		return mBirth;
	}

	public void setBirth(final Date pBirth) {
		mBirth = pBirth;
	}

	public String getBirthStr() {
		return mBirthStr;
	}

	public void setBirthStr(final String pBirthStr) {
		mBirthStr = pBirthStr;
	}

	public String getRingNumber() {
		return mRingNumber;
	}

	public void setRingNumber(final String pRingNumber) {
		mRingNumber = pRingNumber;
	}

	public String getRingPhone() {
		return mRingPhone;
	}

	public void setRingPhone(final String pRingPhone) {
		mRingPhone = pRingPhone;
	}

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public ParentStudentRelationship getParentStudentRelationship() {
		return mParentStudentRelationship;
	}

	public void setParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship) {
		mParentStudentRelationship = pParentStudentRelationship;
	}

	public FmcClass getFmcClass() {
		return mFmcClass;
	}

	public void setFmcClass(FmcClass pFmcClass) {
		mFmcClass = pFmcClass;
	}

	public School getSchool() {
		return mSchool;
	}

	public void setSchool(School pSchool) {
		mSchool = pSchool;
	}

	public String getIdentifyCode() {
		return mIdentifyCode;
	}

	public void setIdentifyCode(String pIdentifyCode) {
		mIdentifyCode = pIdentifyCode;
	}
}
