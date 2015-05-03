package com.fmc.edu.model.profile;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
public class TempParentProfile extends BaseBean {

	private String mPhone;

	private String mIdentifyingCode;

	private Timestamp mIdentifyDate;

	/*
	 it's the first time registered
	 */
	private Timestamp mCreationDate;

	public TempParentProfile() {
	}

	public TempParentProfile(final String pPhone, final String pIdentifyingCode) {
		mPhone = pPhone;
		mIdentifyingCode = pIdentifyingCode;
	}

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Timestamp getIdentifyDate() {
		return mIdentifyDate;
	}

	public void setIdentifyDate(final Timestamp pIdentifyDate) {
		mIdentifyDate = pIdentifyDate;
	}

	public String getIdentifyingCode() {
		return mIdentifyingCode;
	}

	public void setIdentifyingCode(final String pIdentifyingCode) {
		mIdentifyingCode = pIdentifyingCode;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(final String pPhone) {
		mPhone = pPhone;
	}
}
