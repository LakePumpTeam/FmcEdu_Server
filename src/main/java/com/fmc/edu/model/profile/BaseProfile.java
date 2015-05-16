package com.fmc.edu.model.profile;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/5/2015.
 */
public class BaseProfile extends BaseBean {

	private String mName;

	private String mPhone;

	private String mPassword;

	private String mAppId;

	private Timestamp mCreationDate;

	private Timestamp mLastLoginDate;

	private boolean mAvailable;

	private int mProfileType;

	public String getName() {
		return mName;
	}

	public void setName(final String pName) {
		mName = pName;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(final String pPhone) {
		mPhone = pPhone;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(final String pPassword) {
		mPassword = pPassword;
	}

	public String getAppId() {
		return mAppId;
	}

	public void setAppId(final String pAppId) {
		mAppId = pAppId;
	}

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Timestamp getLastLoginDate() {
		return mLastLoginDate;
	}

	public void setLastLoginDate(final Timestamp pLastLoginDate) {
		mLastLoginDate = pLastLoginDate;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public int getProfileType() {
		return mProfileType;
	}

	public void setProfileType(final int pProfileType) {
		mProfileType = pProfileType;
	}
}
