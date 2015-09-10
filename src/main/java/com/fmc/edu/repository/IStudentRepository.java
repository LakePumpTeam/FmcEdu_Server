package com.fmc.edu.repository;

import com.fmc.edu.model.relationship.ParentStudentRelationship;
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

    String QUERY_STUDENTS_BY_CLASS_ID = "com.fmc.edu.student.queryStudentsByClassId";

    Map<String, Object> queryStudentsByClassId(int pClassId);

    String QUERY_PARENT_STUDENT_RELATIONSHIP_BY_STUDENT_ID = "com.fmc.edu.student.queryParentStudentRelationshipByStudentId";

    List<ParentStudentRelationship> queryParentStudentRelationshipByStudentId(final int pStudentId);

    String LOAD_CLASS_STUDENTS = "com.fmc.edu.student.loadClassStudents";

    List<Student> loadClassStudents(int pClassId);

    String QUERY_STUDENT_BY_IDENTIFY_CODE = "com.fmc.edu.student.queryStudentByIdentifyCode";

    Student queryStudentByIdentifyCode(String pIdentifyCode);
}
