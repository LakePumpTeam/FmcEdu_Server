package com.fmc.edu.model.student;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Yove on 5/8/2015.
 */
public class Student extends BaseBean {

	private int mClassId;

	private String mName;

	private boolean mFemale;

	private Date mBirth;

	private String mRingNumber;

	private String mRingPhone;

	private Timestamp mCreationDate;

	private boolean mAvailable;

	public Student() {
	}

	public Student(final int pClassId, final String pName) {
		mClassId = pClassId;
		mName = pName;
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

	public boolean isFemale() {
		return mFemale;
	}

	public void setFemale(final boolean pFemale) {
		mFemale = pFemale;
	}

	public Date getBirth() {
		return mBirth;
	}

	public void setBirth(final Date pBirth) {
		mBirth = pBirth;
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
}
