package com.fmc.edu.model.school;

import com.fmc.edu.model.BaseBean;
import com.fmc.edu.model.address.Address;

/**
 * Created by Yu on 5/12/2015.
 */
public class School extends BaseBean {

	private String mName;

	private int mProvinceId;

	private int mCityId;

	private int mAddressId;

	private String mAddress;

	public School() {

	}

	public Address extractAddress() {
		Address address = new Address(mProvinceId, mCityId, mAddress);
		address.setId(mAddressId);
		return address;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public int getProvinceId() {
		return mProvinceId;
	}

	public void setProvinceId(int pProvinceId) {
		mProvinceId = pProvinceId;
	}

	public int getCityId() {
		return mCityId;
	}

	public void setCityId(int pCityId) {
		mCityId = pCityId;
	}

	public int getAddressId() {
		return mAddressId;
	}

	public void setAddressId(int pAddressId) {
		mAddressId = pAddressId;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(final String pAddress) {
		mAddress = pAddress;
	}
}
