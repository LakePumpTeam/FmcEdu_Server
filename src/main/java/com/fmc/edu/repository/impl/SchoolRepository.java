package com.fmc.edu.repository.impl;

import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
@Repository("schoolRepository")
public class SchoolRepository  extends BaseRepository implements ISchoolRepository {
    @Override
    public List<Map<String, String>> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("cityId", pCityId);
        int count = getSqlSession().selectOne(QUERY_SCHOOL_COUNT, params);
        if (count > 0) {
            List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_SCHOOL_PAGE, params);
            //addIsLastPageFlag(queryResult, pPagination.getPageSize());
            return queryResult;
        }
        return null;
    }

    @Override
    public List<Map<String, String>> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("schoolId", pSchoolId);
        int count = getSqlSession().selectOne(QUERY_CLASS_COUNT, params);
        if (count > 0) {
            List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_CLASS_PAGE, params);
            //addIsLastPageFlag(queryResult, pPagination.getPageSize());
            return queryResult;
        }
        return null;
    }

    @Override
    public List<Map<String, String>> queryHeadmasterPage(Pagination pPagination, int pClassId, final int pSchoolId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("classId", pClassId);
        params.put("schoolId", pSchoolId);
       return getSqlSession().selectList(QUERY_SCHOOL_PAGE, params);
    }
}
