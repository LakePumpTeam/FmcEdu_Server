package com.fmc.edu.executor.handler;

import com.fmc.edu.admin.builder.SelectPaginationBuilder;
import com.fmc.edu.executor.IInitializationHandler;
import com.fmc.edu.manager.LocationManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 6/28/2015.
 */
public class LocationInitHandler implements IInitializationHandler {

	@Resource(name = "locationManager")
	private LocationManager mLocationManager;

	private static final Logger LOG = Logger.getLogger(LocationInitHandler.class);

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		Map<String, Object> provincePageMap = getLocationManager().queryProvincePage(SelectPaginationBuilder.getSelectiPagination(), "");
		List<Map<String, Object>> provinceList = (List<Map<String, Object>>) provincePageMap.get("provinces");
		Map<String, Object> cityPageMap = getLocationManager().queryCityPage(SelectPaginationBuilder.getSelectiPagination(), 0, "");
		List<Map<String, Object>> cityList = (List<Map<String, Object>>) cityPageMap.get("cities");
		// map result which would be cached in memory
		Map<String, Object> locationMap = new HashMap();
		// fill data
		for (Map<String, Object> provinceMap : provinceList) {
			String provinceId = String.valueOf(provinceMap.get("provId"));
			locationMap.put(provinceId, provinceMap);
			Map<String, Object> cities = new HashMap<>();
			for (Map<String, Object> cityMap : cityList) {
				String cityProvinceId = String.valueOf(cityMap.get("provinceId"));
				if (StringUtils.endsWith(provinceId, cityProvinceId)) {
					cities.put(provinceId, cityMap);
				}
			}
			provinceMap.put("cities", cities);
		}
		// cached into memory
		ServletContext servletContext = pWebApplicationContext.getServletContext();
		servletContext.setAttribute("locationMap", locationMap);
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}

	public void setLocationManager(final LocationManager pLocationManager) {
		mLocationManager = pLocationManager;
	}
}
