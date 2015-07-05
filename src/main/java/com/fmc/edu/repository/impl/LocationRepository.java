package com.fmc.edu.repository.impl;

import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ILocationRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Repository(value = "locationRepository")
public class LocationRepository extends BaseRepository implements ILocationRepository {

	@Override
	public Map<String, Object> queryProvincePage(Pagination pPagination, String pKey) {
		Map<String, Object> params = paginationToParameters(pPagination);
		if (StringUtils.isNotBlank(pKey)) {
			params.put("key", pKey);
		}
		params.put("key", pKey);
		Map<String, Object> dataMap = new HashMap<String, Object>(1);
		List<Map<String, String>> queryResult = new ArrayList<Map<String, String>>(0);
		int count = getSqlSession().selectOne(FILTER_PROVINCE_COUNT, params);
		if (count > 0) {
			queryResult = getSqlSession().selectList(FILTER_PROVINCE_PAGE, params);
		}
		dataMap.put("provinces", queryResult);
		addIsLastPageFlag(dataMap, queryResult, pPagination.getPageSize());
		return dataMap;
	}

	@Override
	public Map<String, Object> queryCityPage(final Pagination pPagination, final int pProvId, final String pKey) {
		Map<String, Object> params = paginationToParameters(pPagination);
		if (StringUtils.isNotBlank(pKey)) {
			params.put("key", pKey);
		}
		params.put("provId", pProvId);
		Map<String, Object> dataMap = new HashMap<String, Object>(2);
		List<Map<String, String>> queryResult = new ArrayList<Map<String, String>>(0);
		int count = getSqlSession().selectOne(FILTER_CITY_COUNT, params);
		if (count > 0) {
			queryResult = getSqlSession().selectList(FILTER_CITY_PAGE, params);
		}
		dataMap.put("cities", queryResult);
		addIsLastPageFlag(dataMap, queryResult, pPagination.getPageSize());
		return dataMap;
	}
}
