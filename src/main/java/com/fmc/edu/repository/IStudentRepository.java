package com.fmc.edu.repository;

import com.fmc.edu.model.student.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
public interface IStudentRepository {

    String QUERY_STUDENTS_BY_PARENT_ID = "com.fmc.edu.student.queryStudentsByParentId";

    List<Student> queryStudentByParentId(final int pParentId);

    String QUERY_STUDENT_LIST_BY_TEACHER_ID = "com.fmc.edu.student.queryStudentListByTeacherId";

    Map<String, Object> queryStudentListByTeacherId(int pTeacherId);

    String QUERY_STUDENT_BY_ID = "com.fmc.edu.student.queryStudentById";

    Student queryStudentById(int pStudentId);
}
