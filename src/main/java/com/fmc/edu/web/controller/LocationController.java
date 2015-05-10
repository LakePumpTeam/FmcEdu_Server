package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.LocationManager;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {

	@Resource(name = "locationManager")
	private LocationManager mLocationManager;

	@RequestMapping(value = ("/requestProv" + GlobalConstant.URL_SUFFIX))
	@ResponseBody
	public String requestProv(final HttpServletRequest pRequest, final HttpServletResponse pResponse, String filterKey ) throws IOException {
		String key = decodeInput(filterKey);
		Pagination pagination = buildPagination(pRequest);
		List<Map<String, String>> cities = getLocationManager().queryProvincePage(pagination, key);
		return generateJsonOutput(Boolean.TRUE, cities, null);
	}

	@RequestMapping(value = ("/requestCitys" + GlobalConstant.URL_SUFFIX))
	@ResponseBody
	public String requestCityPage(final HttpServletRequest pRequest, final HttpServletResponse pResponse,final String provId, String filterKey ) throws IOException {
		String key = decodeInput(filterKey);
		String provinceId = decodeInput(provId);
		Pagination pagination = buildPagination(pRequest);
		List<Map<String, String>> cities = getLocationManager().queryCityPage(pagination,Integer.valueOf(provinceId), key);
		return generateJsonOutput(Boolean.TRUE, cities, null);
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}

	public void setLocationManager(final LocationManager pLocationManager) {
		mLocationManager = pLocationManager;
	}
}