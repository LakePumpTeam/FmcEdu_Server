package com.fmc.edu.admin.controller;

import com.fmc.edu.admin.builder.SelectPaginationBuilder;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.util.pagenation.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Yove on 7/15/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminLocationController {

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;

	@RequestMapping("/cities" + GlobalConstant.URL_SUFFIX)
	@ResponseBody
	public String requestCities(HttpServletRequest pRequest, HttpServletResponse pResponse, String provinceId) {
		WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = applicationContext.getServletContext();
		Map<String, Object> locationMap = (Map<String, Object>) servletContext.getAttribute("locationMap");
		Map<String, Object> provinceMap = (Map<String, Object>) locationMap.get(provinceId);
		Map<String, Object> cityMap = null;
		if (MapUtils.isNotEmpty(provinceMap)) {
			cityMap = (Map<String, Object>) provinceMap.get("cities");
		} else {
			cityMap = Collections.EMPTY_MAP;
		}
		String citiesJson = JSONObject.fromObject(cityMap).toString();
		return citiesJson;
	}

	@RequestMapping("/schools" + GlobalConstant.URL_SUFFIX)
	@ResponseBody
	public String requestSchools(HttpServletRequest pRequest, HttpServletResponse pResponse, int cityId) {
		Pagination pagination = SelectPaginationBuilder.getSelectPagination();
		Object schools = getSchoolManager().querySchoolsPage(pagination, cityId, null).get("schools");
		String schoolJson = "{}";
		if (schools != null) {
			schoolJson = JSONArray.fromObject(schools).toString();
		}
		return schoolJson;
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}
}
