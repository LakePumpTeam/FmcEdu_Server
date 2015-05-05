package com.fmc.edu.model.profile;

/**
 * Created by Yove on 5/5/2015.
 */
public enum ProfileType {

	TEMP_PARENT, PARENT, TEACHER;

	public String getName() {
		return this.name();
	}


	public int getValue() {
		return this.ordinal();
	}
}
