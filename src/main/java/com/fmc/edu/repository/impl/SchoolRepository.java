package com.fmc.edu.repository.impl;

import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
@Repository("schoolRepository")
public class SchoolRepository  extends BaseRepository implements ISchoolRepository {
    @Override
    public Map<String,Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("cityId", pCityId);
        int count = getSqlSession().selectOne(QUERY_SCHOOL_COUNT, params);

        Map<String, Object> dataList = new HashMap<String, Object>(2);
        List<Map<String, String>> queryResult = new ArrayList<Map<String, String>>(0);
        if (count > 0) {
            queryResult = getSqlSession().selectList(QUERY_SCHOOL_PAGE, params);
        }

        dataList.put("schools", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public Map<String,Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("schoolId", pSchoolId);
        int count = getSqlSession().selectOne(QUERY_CLASS_COUNT, params);
        Map<String, Object> dataList = new HashMap<String, Object>(2);
        List<Map<String, String>> queryResult = new ArrayList<Map<String, String>>(0);
        if (count > 0) {
            queryResult = getSqlSession().selectList(QUERY_CLASS_PAGE, params);
        }
        dataList.put("classes", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public List<Map<String, String>> queryHeadmasterPage(int pClassId, final int pSchoolId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("classId", pClassId);
        params.put("schoolId", pSchoolId);
       return getSqlSession().selectList(QUERY_HEADMASTER, params);
    }
}
