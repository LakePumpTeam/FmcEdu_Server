package com.fmc.edu.repository;

import com.fmc.edu.model.student.Student;

import java.util.List;

/**
 * Created by Yu on 5/12/2015.
 */
public interface IStudentRepository {

    String QUERY_STUDENTS_BY_PARENT_ID = "com.fmc.edu.student.queryStudentsByParentId";

    List<Student> queryStudentByParentId(final int pParentId);
}
