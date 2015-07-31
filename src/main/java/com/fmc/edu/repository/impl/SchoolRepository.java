package com.fmc.edu.repository.impl;

import com.fmc.edu.model.course.Course;
import com.fmc.edu.model.course.TimeTable;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;
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
    public Map<String, Object> queryHeadmasterPage(final int pClassId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("classId", pClassId);
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

    @Override
    public TeacherProfile queryTeacherById(final int pTeacherId) {
        return getSqlSession().selectOne(QUERY_TEACHER_BY_ID, pTeacherId);
    }

    @Override
    public List<Course> queryCourseListByClassId(int pClassId, int pWeek) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("classId", pClassId);
        params.put("week", pWeek);
        return getSqlSession().selectList(QUERY_COURSE_LIST_BY_CLASS_ID, params);
    }

    @Override
    public FmcClass queryDefaultClassBySchoolId(final int pSchoolId) {
        return getSqlSession().selectOne(QUERY_DEFAULT_CLASS_BY_SCHOOL_ID, pSchoolId);
    }

    @Override
    public List<BaseProfile> queryAllParentByClassId(int pClassId) {
        return getSqlSession().selectList(QUERY_ALL_PARENT_BY_CLASS_ID, pClassId);
    }

    @Override
    public int insertTimeTable(TimeTable pTimeTable) {
        return getSqlSession().insert(INSERT_TIME_TABLE, pTimeTable);
    }

    @Override
    public int insertCourse(Course pCourse) {
        return getSqlSession().insert(INSERT_COURSE, pCourse);
    }

    @Override
    public int updateCourse(Course pCourse) {
        return getSqlSession().update(UPDATE_COURSE, pCourse);
    }
}
