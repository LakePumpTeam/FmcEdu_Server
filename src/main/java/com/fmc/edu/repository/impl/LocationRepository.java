package com.fmc.edu.repository.impl;

import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ILocationRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Repository(value = "locationRepository")
public class LocationRepository extends BaseRepository implements ILocationRepository {

	@Override
	public List<Map<String, String>> queryCityPage(final Pagination pPagination, final String pKey) {
		Map<String, Object> params = paginationToParameters(pPagination);
		params.put("key", pKey);
		int count = getSqlSession().selectOne(FILTER_CITY_COUNT, params);
		if (count > 0) {
			return getSqlSession().selectList(FILTER_CITY_PAGE, params);
		}
		return null;
	}
}
