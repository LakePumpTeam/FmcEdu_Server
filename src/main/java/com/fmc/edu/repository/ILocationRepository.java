package com.fmc.edu.repository;

import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
public interface ILocationRepository {

	String FILTER_CITY_COUNT = "com.fmc.edu.location.filterCityCount";
	String FILTER_CITY_PAGE = "com.fmc.edu.location.filterCityPage";

	List<Map<String, String>> queryCityPage(final Pagination pPagination, final String pKey);
}
