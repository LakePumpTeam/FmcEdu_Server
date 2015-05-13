package com.fmc.edu.model.profile;

/**
 * Created by Yove on 5/5/2015.
 */
public enum ProfileType {

	TEMP_PARENT(9), PARENT(2), TEACHER(1);

	private int mValue;

	ProfileType(int pValue) {
		this.mValue = pValue;
	}

	public String getName() {
		return this.name();
	}


	public int getValue() {
		return this.mValue;
	}
}
