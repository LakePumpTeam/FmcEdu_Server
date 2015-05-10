package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class UserRole extends BaseBean implements Serializable {

	private int mUserId;
	private int mRoleId;

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int pUserId) {
		mUserId = pUserId;
	}

	public int getRoleId() {
		return mRoleId;
	}

	public void setRoleId(int pRoleId) {
		mRoleId = pRoleId;
	}
}
