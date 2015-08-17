package com.fmc.edu.service.impl;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.repository.ILocationRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Service(value = "locationService")
public class LocationService {

	@Resource(name = "locationRepository")
	private ILocationRepository mLocationRepository;

	public Map<String, Object> queryProvincePage(final Pagination pPagination, final String pKey) {
		return getLocationRepository().queryProvincePage(pPagination, pKey);
	}
	public  Map<String, Object> queryCityPage(final Pagination pPagination,final int pProvId, final String pKey) {
		return getLocationRepository().queryCityPage(pPagination, pProvId, pKey);
	}

	public ILocationRepository getLocationRepository() {
		return mLocationRepository;
	}

	public void setLocationRepository(final ILocationRepository pLocationRepository) {
		mLocationRepository = pLocationRepository;
	}

	public boolean updateAddress(final Address pAddress) {
		return getLocationRepository().updateAddress(pAddress);
	}

	public boolean createAddress(final Address pAddress) {
		return getLocationRepository().createAddress(pAddress);
	}
}
