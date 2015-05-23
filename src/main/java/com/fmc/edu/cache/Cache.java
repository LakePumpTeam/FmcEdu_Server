package com.fmc.edu.cache;

/**
 * Created by Yove on 5/23/2015.
 */
public class Cache {

	private String mKey;

	private Object mValue;

	private long mLastUpdateTime;

	public Cache(final String pKey, final Object pValue) {
		mKey = pKey;
		mValue = pValue;
		mLastUpdateTime = System.currentTimeMillis();
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
