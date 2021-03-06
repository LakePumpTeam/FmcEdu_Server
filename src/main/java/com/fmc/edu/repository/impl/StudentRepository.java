package com.fmc.edu.repository.impl;

import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IStudentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository(value = "studentRepository")
public class StudentRepository extends BaseRepository implements IStudentRepository {

	@Override
	public List<Student> queryStudentByParentId(int pParentId) {
		return getSqlSession().selectList(QUERY_STUDENTS_BY_PARENT_ID, pParentId);
	}

	@Override
	public Map<String, Object> queryStudentListByTeacherId(int pTeacherId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("teacherId", pTeacherId);
		Map<String, Object> dataList = new HashMap<String, Object>(1);
		List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_STUDENT_LIST_BY_TEACHER_ID, params);
		dataList.put("studentList", queryResult);
		return dataList;
	}

	@Override
	public Student queryStudentById(int pStudentId) {
		return getSqlSession().selectOne(QUERY_STUDENT_BY_ID, pStudentId);
	}

	@Override
	public Map<String, Object> queryStudentsByClassId(final int pClassId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("classId", pClassId);
		Map<String, Object> dataList = new HashMap<String, Object>(1);
		List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_STUDENTS_BY_CLASS_ID, params);
		dataList.put("studentList", queryResult);
		return dataList;
	}

	@Override
	public List<ParentStudentRelationship> queryParentStudentRelationshipByStudentId(int pStudentId) {
		return getSqlSession().selectList(QUERY_PARENT_STUDENT_RELATIONSHIP_BY_STUDENT_ID, pStudentId);
	}

	@Override
	public List<Student> loadClassStudents(final int pClassId) {
		return getSqlSession().selectList(LOAD_CLASS_STUDENTS, pClassId);
	}

	@Override
	public Student queryStudentByIdentifyCode(String pIdentifyCode) {
		return getSqlSession().selectOne(QUERY_STUDENT_BY_IDENTIFY_CODE, pIdentifyCode);
	}

	@Override
	public boolean updateStudent(final Student pStudent) {
		return getSqlSession().update(UPDATE_STUDENT, pStudent) > 0;
	}

	@Override
	public boolean createStudent(final Student pStudent) {
		return getSqlSession().insert(CREATE_STUDENT, pStudent) > 0;
	}
}
