package com.fmc.edu.model.profile;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
public class TempParentProfile extends BaseProfile {


	private String mIdentifyingCode;

	private Timestamp mIdentifyDate;

	private boolean isIdentified() {
		return mIdentifyDate != null;
	}

	public TempParentProfile() {
		setProfileType(ProfileType.TEMP_PARENT);
	}

	public TempParentProfile(String pPhoneNumber, String pIdentifyingCode) {
		setProfileType(ProfileType.TEMP_PARENT);
		setPhone(pPhoneNumber);
		this.mIdentifyingCode = pIdentifyingCode;
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

}
