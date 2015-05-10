package com.fmc.edu.repository;

import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
public interface ISchoolRepository {
    String QUERY_SCHOOL_COUNT = "com.fmc.edu.school.querySchoolCount";
    String QUERY_SCHOOL_PAGE = "com.fmc.edu.school.querySchoolPage";
    List<Map<String, String>> querySchoolsPage(final Pagination pPagination, final int pCityId, final String pKey);

    String QUERY_CLASS_COUNT = "com.fmc.edu.school.queryClassCount";
    String QUERY_CLASS_PAGE = "com.fmc.edu.school.queryClassPage";
    List<Map<String, String>> queryClassesPage(final Pagination pPagination,final int pSchoolId, final String pKey);

    String FILTER_PROVINCE_COUNT = "com.fmc.edu.school.queryHeadmaster";
    List<Map<String, String>> queryHeadmasterPage(final Pagination pPagination,final int pClassId, final int pSchoolId, final String pKey);
}
