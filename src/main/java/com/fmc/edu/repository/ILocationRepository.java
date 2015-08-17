package com.fmc.edu.repository;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.util.pagenation.Pagination;

import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
public interface ILocationRepository {

	String FILTER_PROVINCE_COUNT = "com.fmc.edu.location.filterProvCount";
	String FILTER_PROVINCE_PAGE = "com.fmc.edu.location.filterProvPage";

	Map<String, Object> queryProvincePage(final Pagination pPagination, final String pKey);

	String FILTER_CITY_COUNT = "com.fmc.edu.location.filterCityCount";
	String FILTER_CITY_PAGE = "com.fmc.edu.location.filterCityPage";

	Map<String, Object> queryCityPage(final Pagination pPagination,final int pProvId, final String pKey);

	String CREATE_ADDRESS = "com.fmc.edu.location.createAddress";

	boolean createAddress(Address pAddress);

	String UPDATE_ADDRESS = "com.fmc.edu.location.updateAddress";

	boolean updateAddress(Address pAddress);
}
