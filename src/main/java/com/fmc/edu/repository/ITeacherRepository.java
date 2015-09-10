package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
public interface ITeacherRepository {

	String QUERY_TEACHER_BY_ID = "com.fmc.edu.teacher.queryTeacherById";

	TeacherProfile queryTeacherById(final int pTeacherId);

	String QUERY_HEADER_TEACHER_BY_PARENT_ID = "com.fmc.edu.teacher.queryHeaderTeacherByParentId";

	List<Map<String, Object>> queryHeaderTeacherByParentId(final int parentId);

	String QUERY_CLASS_BY_TEACHER_ID = "com.fmc.edu.teacher.queryClassByTeacherId";

	List<Map<String, Object>> queryClassByTeacherId(int pTeacherId);

	String UPDATE_TEACHER = "com.fmc.edu.teacher.updateTeacher";

	boolean updateTeacher(TeacherProfile pTeacher);

	String QUERY_CLASS_BY_ID = "com.fmc.edu.teacher.queryClassById";

	FmcClass queryClassById(int pClassId);

	String QUERY_TEACHERS_BY_SCHOOL_ID = "com.fmc.edu.teacher.queryTeachersBySchoolId";

	List<TeacherProfile> queryTeachersBySchoolId(int pSchoolId);

	String QUERY_TEACHER_CLASS_RELATIONSHIPS = "com.fmc.edu.teacher.queryTeacherClassRelationships";

	List<TeacherClassRelationship> queryTeacherClassRelationships(int pTeacherId);

	String UPDATE_TEACHER_PROFILE = "com.fmc.edu.teacher.updateTeacherProfile";

	boolean updateTeacherProfile(TeacherProfile pTeacher);

	String UPDATE_TEACHER_DETAIL = "com.fmc.edu.teacher.updateTeacherDetail";

	boolean updateTeacherDetail(TeacherProfile pTeacher);

	String INITIAL_TEACHER_PROFILE = "com.fmc.edu.teacher.initialTeacherProfile";

	int createTeacherProfile(final TeacherProfile pTeacher);

	String INITIAL_TEACHER = "com.fmc.edu.teacher.initialTeacher";

	boolean createTeacherDetail(TeacherProfile pTeacher);

	String CREATE_TEACHER_CLASS_RELATIONSHIP = "com.fmc.edu.teacher.createTeacherClassRelationship";

	int createTeacherClassRelationship(TeacherClassRelationship pTeacherClassRelationship);

	String RESET_ALL_HEADTEACHER_RELATIONSHIP = "com.fmc.edu.teacher.resetAllHeadTeacherRelationship";

	void resetAllHeadTeacherRelationship(int pClassId);

	String UPDATE_HEADTEACHER_RELATIONSHIP = "com.fmc.edu.teacher.updateHeadTeacherRelationship";

	boolean updateHeadTeacherRelationship(int pClassId, int pTeacherId);
}
