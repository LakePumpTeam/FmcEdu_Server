package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Repository;

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
	public boolean createTeacherProfile(final TeacherProfile pTeacher) {
		return getSqlSession().insert(INITIAL_TEACHER_PROFILE, pTeacher) > 0;
	}
}
