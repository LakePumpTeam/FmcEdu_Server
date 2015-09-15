package com.fmc.edu.executor.handler;

import com.fmc.edu.admin.builder.SelectPaginationBuilder;
import com.fmc.edu.executor.IInitializationHandler;
import com.fmc.edu.manager.LocationManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.school.School;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Yove on 6/28/2015.
 */
public class LocationInitHandler implements IInitializationHandler {

	@Resource(name = "locationManager")
	private LocationManager mLocationManager;

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;

	private static final Logger LOG = Logger.getLogger(LocationInitHandler.class);

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		Map<String, Object> provincePageMap = getLocationManager().queryProvincePage(SelectPaginationBuilder.getSelectPagination(), "");
		List<Map<String, Object>> provinceList = (List<Map<String, Object>>) provincePageMap.get("provinces");
		Map<String, Object> cityPageMap = getLocationManager().queryCityPage(SelectPaginationBuilder.getSelectPagination(), 0, "");
		List<Map<String, Object>> cityList = (List<Map<String, Object>>) cityPageMap.get("cities");
		// used to query schools of default city
		String defaultCityId = null;
		// map result which would be cached in memory
		Map<String, Object> locationMap = new TreeMap<>();
		// fill data
		for (Map<String, Object> provinceMap : provinceList) {
			String provinceId = String.valueOf(provinceMap.get("provId"));
			locationMap.put(provinceId, provinceMap);
			Map<String, Object> cities = new TreeMap<>();
			for (Map<String, Object> cityMap : cityList) {
				String cityProvinceId = String.valueOf(cityMap.get("provinceId"));
				if (StringUtils.equals(provinceId, cityProvinceId)) {
					String cityId = String.valueOf(cityMap.get("cityId"));
					cities.put(cityId, cityMap);
					if (defaultCityId == null) {
						defaultCityId = cityId;
					}
				}
			}
			provinceMap.put("cities", cities);
		}

		// cache into memory
		ServletContext servletContext = pWebApplicationContext.getServletContext();
		servletContext.setAttribute("locationMap", locationMap);

		// query schools of default city
		int cityId = RepositoryUtils.safeParseId(defaultCityId);
		if (RepositoryUtils.idIsValid(cityId)) {
			List<School> defaultSchools = getSchoolManager().querySchools(cityId, StringUtils.EMPTY);
			servletContext.setAttribute("schoolsOfDefaultCity", defaultSchools);
		}
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}

	public void setLocationManager(final LocationManager pLocationManager) {
		mLocationManager = pLocationManager;
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}
}
