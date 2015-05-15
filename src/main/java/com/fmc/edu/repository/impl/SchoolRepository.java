package com.fmc.edu.repository.impl;

import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
@Repository("schoolRepository")
public class SchoolRepository extends BaseRepository implements ISchoolRepository {
    @Override
    public Map<String, Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("cityId", pCityId);
        int count = getSqlSession().selectOne(QUERY_SCHOOL_COUNT, params);

        Map<String, Object> dataList = new HashMap<String, Object>(2);
        List<Map<String, String>> queryResult = null;
        if (count > 0) {
            queryResult = getSqlSession().selectList(QUERY_SCHOOL_PAGE, params);
        }

        dataList.put("schools", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public Map<String, Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("key", pKey);
        params.put("schoolId", pSchoolId);
        int count = getSqlSession().selectOne(QUERY_CLASS_COUNT, params);
        Map<String, Object> dataList = new HashMap<String, Object>(2);
        List<Map<String, String>> queryResult = null;
        if (count > 0) {
            queryResult = getSqlSession().selectList(QUERY_CLASS_PAGE, params);
        }
        dataList.put("classList", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public Map<String, Object> queryHeadmasterPage(final int pSchoolId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("schoolId", pSchoolId);
        return getSqlSession().selectOne(QUERY_HEADMASTER, params);
    }

    @Override
    public boolean updateStudentById(final Student pStudent) {
        return getSqlSession().update(UPDATE_STUDENT_BY_ID, pStudent) > 0;
    }

    @Override
    public int queryStudentIdByFields(final Student pStudent) {
        List<Integer> result = getSqlSession().selectList(QUERY_STUDENT_BY_FIELDS, pStudent);
        int id = 0;
        if (CollectionUtils.isNotEmpty(result)) {
            id = result.get(0);
        }
        return id;
    }

    @Override
    public boolean initialStudent(final Student pStudent) {
        pStudent.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return getSqlSession().insert(INITIAL_STUDENT, pStudent) > 0;
    }
}
