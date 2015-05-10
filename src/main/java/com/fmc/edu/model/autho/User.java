package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class User extends BaseBean implements Serializable {
	private String mUsername;
	private String mPassword;

	private Boolean mLocked = Boolean.FALSE;

	public User() {
	}

	public User(final String pUsername, final String pPassword) {
		this.mUsername = pUsername;
		this.mPassword = pPassword;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String pUsername) {
		this.mUsername = pUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String pPassword) {
		this.mPassword = pPassword;
	}

	public Boolean getLocked() {
		return mLocked;
	}

	public void setmLocked(Boolean mLocked) {
		this.mLocked = mLocked;
	}
}
