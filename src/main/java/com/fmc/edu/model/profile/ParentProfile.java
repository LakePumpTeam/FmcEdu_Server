package com.fmc.edu.model.profile;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/8/2015.
 */
public class ParentProfile extends BaseProfile {

	private int mAddressId;

	private boolean mPaid;

	private Timestamp mFreeTrialEndDate;

	private boolean mFreeTrial;

	public int getAddressId() {
		return mAddressId;
	}

	public void setAddressId(final int pAddressId) {
		mAddressId = pAddressId;
	}

	public boolean isPaid() {
		return mPaid;
	}

	public void setPaid(final boolean pPaid) {
		mPaid = pPaid;
	}

	public Timestamp getFreeTrialEndDate() {
		return mFreeTrialEndDate;
	}

	public void setFreeTrialEndDate(final Timestamp pFreeTrialEndDate) {
		mFreeTrialEndDate = pFreeTrialEndDate;
	}

	public boolean isFreeTrial() {
		return mFreeTrial;
	}

	public void setFreeTrial(final boolean pFreeTrial) {
		mFreeTrial = pFreeTrial;
	}
}
