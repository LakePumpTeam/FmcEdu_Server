package com.fmc.edu.model;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
public class BaseBean {

	private int mId;

	private Timestamp mLastUpdateDate = new Timestamp(System.currentTimeMillis());

	public BaseBean() {
	}

	public BaseBean(final int pId) {
		mId = pId;
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public Timestamp getLastUpdateDate() {
		return mLastUpdateDate;
	}

	public void setLastUpdateDate(final Timestamp pLastUpdateDate) {
		mLastUpdateDate = pLastUpdateDate;
	}
}
