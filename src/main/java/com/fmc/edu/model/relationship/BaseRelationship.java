package com.fmc.edu.model.relationship;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/12/2015.
 */
public class BaseRelationship {


	private Timestamp mCreationDate = new Timestamp(System.currentTimeMillis());

	private Timestamp mLastUpdateDate = new Timestamp(System.currentTimeMillis());

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Timestamp getLastUpdateDate() {
		return mLastUpdateDate;
	}

	public void setLastUpdateDate(final Timestamp pLastUpdateDate) {
		mLastUpdateDate = pLastUpdateDate;
	}
}
