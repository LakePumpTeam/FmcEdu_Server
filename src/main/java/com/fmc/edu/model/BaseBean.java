package com.fmc.edu.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
public class BaseBean implements Serializable {

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

	@Override
	public boolean equals(Object pObject) {
		if (this == pObject) {
			return true;
		}
		if (pObject == null || getClass() != pObject.getClass()) {
			return false;
		}

		BaseBean baseBean = (BaseBean) pObject;
		return getId() == baseBean.getId();
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(getId()).hashCode();
	}

}
