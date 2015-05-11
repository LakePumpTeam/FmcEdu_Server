package com.fmc.edu.model.relationship;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/12/2015.
 */
public class ParentStudentRelationship extends BaseRelationship {

	private int mParentId;

	private String mParentPhone;

	private int mStudentId;

	private String mRelationship;

	private boolean mApproved;

	private Timestamp mApprovedDate;

	public ParentStudentRelationship() {
	}

	public ParentStudentRelationship(final String pParentPhone, final String pRelationship) {
		mParentPhone = pParentPhone;
		mRelationship = pRelationship;
	}

	public int getParentId() {
		return mParentId;
	}

	public void setParentId(final int pParentId) {
		mParentId = pParentId;
	}

	public String getParentPhone() {
		return mParentPhone;
	}

	public void setParentPhone(final String pParentPhone) {
		mParentPhone = pParentPhone;
	}

	public int getStudentId() {
		return mStudentId;
	}

	public void setStudentId(final int pStudentId) {
		mStudentId = pStudentId;
	}

	public String getRelationship() {
		return mRelationship;
	}

	public void setRelationship(final String pRelationship) {
		mRelationship = pRelationship;
	}

	public boolean isApproved() {
		return mApproved;
	}

	public void setApproved(final boolean pApproved) {
		mApproved = pApproved;
	}

	public Timestamp getApprovedDate() {
		return mApprovedDate;
	}

	public void setApprovedDate(final Timestamp pApprovedDate) {
		mApprovedDate = pApprovedDate;
	}
}
