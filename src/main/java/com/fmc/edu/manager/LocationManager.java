package com.fmc.edu.manager;

import com.fmc.edu.service.impl.LocationService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Service(value = "locationManager")
public class LocationManager {

	@Resource(name = "locationService")
	private LocationService mLocationService;

	public Map<String, Object> queryProvincePage(final Pagination pPagination, final String pKey) {
		return getLocationService().queryProvincePage(pPagination, pKey);
	}

	public Map<String, Object> queryCityPage(final Pagination pPagination, final int pProvId, final String pKey) {
		return getLocationService().queryCityPage(pPagination, pProvId, pKey);
	}

	public LocationService getLocationService() {
		return mLocationService;
	}

	public void setLocationService(final LocationService pLocationService) {
		mLocationService = pLocationService;
	}


}
