package com.fmc.edu.service.impl;

import com.fmc.edu.repository.ILocationRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Service(value = "locationService")
public class LocationService {

	@Resource(name = "locationRepository")
	private ILocationRepository mLocationRepository;

	public List<Map<String, String>> queryCityPage(final Pagination pPagination, final String pKey) {
		return getLocationRepository().queryCityPage(pPagination, pKey);
	}

	public ILocationRepository getLocationRepository() {
		return mLocationRepository;
	}

	public void setLocationRepository(final ILocationRepository pLocationRepository) {
		mLocationRepository = pLocationRepository;
	}
}
