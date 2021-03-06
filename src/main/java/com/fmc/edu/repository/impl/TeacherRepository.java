package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository("teacherRepository")
public class TeacherRepository extends BaseRepository implements ITeacherRepository {

	@Override
	public TeacherProfile queryTeacherById(int pTeacherId) {
		return getSqlSession().selectOne(QUERY_TEACHER_BY_ID, pTeacherId);
	}

	@Override
	public List<Map<String, Object>> queryHeaderTeacherByParentId(int pTeacherId) {
		return getSqlSession().selectList(QUERY_HEADER_TEACHER_BY_PARENT_ID, pTeacherId);
	}

	@Override
	public List<Map<String, Object>> queryClassByTeacherId(int pTeacherId) {
		return getSqlSession().selectList(QUERY_CLASS_BY_TEACHER_ID, pTeacherId);
	}

	@Override
	public boolean updateTeacher(final TeacherProfile pTeacher) {
		return getSqlSession().update(UPDATE_TEACHER, pTeacher) > 0;
	}

	@Override
	public FmcClass queryClassById(int pClassId) {
		return getSqlSession().selectOne(QUERY_CLASS_BY_ID, pClassId);
	}

	@Override
	public List<TeacherProfile> queryTeachersBySchoolId(final int pSchoolId) {
		return getSqlSession().selectList(QUERY_TEACHERS_BY_SCHOOL_ID, pSchoolId);
	}

	@Override
	public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pTeacherId) {
		return getSqlSession().selectList(QUERY_TEACHER_CLASS_RELATIONSHIPS, pTeacherId);
	}

	@Override
	public boolean updateTeacherProfile(final TeacherProfile pTeacher) {
		return getSqlSession().update(UPDATE_TEACHER_PROFILE, pTeacher) > 0;
	}

	@Override
	public boolean updateTeacherDetail(TeacherProfile pTeacher) {
		return getSqlSession().update(UPDATE_TEACHER_DETAIL, pTeacher) > 0;
	}

	@Override
	public boolean createTeacherDetail(final TeacherProfile pTeacher) {
		return getSqlSession().insert(INITIAL_TEACHER, pTeacher) > 0;
	}

	@Override
	public int createTeacherClassRelationship(TeacherClassRelationship pTeacherClassRelationship) {
		return getSqlSession().insert(CREATE_TEACHER_CLASS_RELATIONSHIP, pTeacherClassRelationship);
	}

	@Override
	public int createTeacherProfile(final TeacherProfile pTeacher) {
		int count = getSqlSession().insert(INITIAL_TEACHER_PROFILE, pTeacher);
		if (count > 0) {
			return pTeacher.getId();
		}
		return 0;
	}

	@Override
	public void resetAllHeadTeacherRelationship(final int pClassId) {
		getSqlSession().update(RESET_ALL_HEADTEACHER_RELATIONSHIP, pClassId);
	}

	@Override
	public boolean updateHeadTeacherRelationship(final int pClassId, final int pTeacherId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("classId", pClassId);
		parameters.put("teacherId", pTeacherId);
		return getSqlSession().update(UPDATE_HEADTEACHER_RELATIONSHIP, parameters) > 0;
	}

	@Override
	public List<TeacherProfile> queryTeacherNotInClass(final int pClassId) {
		return getSqlSession().selectList(QUERY_TEACHER_NOT_IN_CLASS, pClassId);
	}

	@Override
	public boolean updateTeacherClassRelationship(final TeacherClassRelationship pRelationship) {
		return getSqlSession().update(UPDATE_TEACHER_CLASS_RELATIONSHIP, pRelationship) > 0;
	}


	@Override
	public boolean updateTeacherClassRelationshipAvailableBatch(final TeacherClassRelationship[] pRelationships) {
		return getSqlSession().update(UPDATE_TEACHER_CLASS_RELATIONSHIP_AVAILABLE_BATCH, pRelationships) > 0;
	}
}
