package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class UserRole extends BaseBean implements Serializable {

	private int mProfileId;
	private int mRoleId;

	public int getProfileId() {
		return mProfileId;
	}

	public void setProfileId(int pProfileId) {
		mProfileId = pProfileId;
	}

	public int getRoleId() {
		return mRoleId;
	}

	public void setRoleId(int pRoleId) {
		mRoleId = pRoleId;
	}
}
