package com.fmc.edu.repository;

import com.fmc.edu.model.course.Course;
import com.fmc.edu.model.course.TimeTable;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
public interface ISchoolRepository {
    String QUERY_SCHOOL_COUNT = "com.fmc.edu.school.querySchoolCount";
    String QUERY_SCHOOL_PAGE = "com.fmc.edu.school.querySchoolPage";

    Map<String, Object> querySchoolsPage(final Pagination pPagination, final int pCityId, final String pKey);

    String QUERY_CLASS_COUNT = "com.fmc.edu.school.queryClassCount";
    String QUERY_CLASS_PAGE = "com.fmc.edu.school.queryClassPage";

    Map<String, Object> queryClassesPage(final Pagination pPagination, final int pSchoolId, final String pKey);

    String QUERY_HEADMASTER = "com.fmc.edu.school.queryHeadmaster";

    Map<String, Object> queryHeadmasterPage(final int pSchoolId);

    String UPDATE_STUDENT_BY_ID = "com.fmc.edu.school.updateStudentById";

    boolean updateStudentById(Student pStudent);

    String QUERY_STUDENT_BY_FIELDS = "com.fmc.edu.school.queryStudentIdByFields";

    int queryStudentIdByFields(Student pStudent);

    String INITIAL_STUDENT = "com.fmc.edu.school.initialStudent";

    boolean initialStudent(Student pStudent);

    String QUERY_TEACHER_BY_ID = "com.fmc.edu.school.queryTeacherById";

    TeacherProfile queryTeacherById(int pTeacherId);

    String QUERY_COURSE_LIST_BY_CLASS_ID = "com.fmc.edu.school.queryCourseListByClassId";

    List<Course> queryCourseListByClassId(int pClassId, int pWeek);

    String INSERT_TIME_TABLE = "com.fmc.edu.school.insertTimeTable";

    int insertTimeTable(TimeTable pTimeTable);

    String INSERT_COURSE = "com.fmc.edu.school.insertCourse";

    int insertCourse(Course pCourse);

    String UPDATE_COURSE = "com.fmc.edu.school.updateCourse";

    int updateCourse(Course pCourse);
}
