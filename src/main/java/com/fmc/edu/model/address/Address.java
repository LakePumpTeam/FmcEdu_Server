package com.fmc.edu.model.address;

import com.fmc.edu.model.BaseBean;

import java.security.Timestamp;

/**
 * Created by Yove on 5/8/2015.
 */
public class Address extends BaseBean {

	private int mProvinceId;

	private int mCityId;

	private String mProvince;

	private String mCity;

	private String mAddress;

	private Timestamp mCreationDate;

	public Address() {
	}

	public Address(final int pProvinceId, final int pCityId, final String pAddress) {
		mProvinceId = pProvinceId;
		mCityId = pCityId;
		mAddress = pAddress;
	}

	public int getProvinceId() {
		return mProvinceId;
	}

	public void setProvinceId(final int pProvinceId) {
		mProvinceId = pProvinceId;
	}

	public int getCityId() {
		return mCityId;
	}

	public void setCityId(final int pCityId) {
		mCityId = pCityId;
	}

	public String getProvince() {
		return mProvince;
	}

	public void setProvince(final String pProvince) {
		mProvince = pProvince;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(final String pCity) {
		mCity = pCity;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(final String pAddress) {
		mAddress = pAddress;
	}

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}
}
