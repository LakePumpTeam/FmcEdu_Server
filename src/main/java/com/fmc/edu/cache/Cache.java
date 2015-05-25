package com.fmc.edu.cache;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/23/2015.
 */
public class Cache {


	/**
	 * always be id
	 */
	private String mKey;

	private Object mValue;

	private long mLastUpdateTime = System.currentTimeMillis();

	public Cache(final String pKey, final Object pValue) {
		mKey = pKey;
		mValue = pValue;
		mLastUpdateTime = System.currentTimeMillis();
	}

	public void updateLastUpdateTime() {
		mLastUpdateTime = System.currentTimeMillis();
	}

	public Timestamp getLastUpdateTimestamp() {
		return new Timestamp(mLastUpdateTime);
	}

	public String getKey() {
		return mKey;
	}

	public void setKey(final String pKey) {
		mKey = pKey;
	}

	public Object getValue() {
		return mValue;
	}

	public void setValue(final Object pValue) {
		mValue = pValue;
	}

	public long getLastUpdateTime() {
		return mLastUpdateTime;
	}

	public void setLastUpdateTime(final long pLastUpdateTime) {
		mLastUpdateTime = pLastUpdateTime;
	}
}
