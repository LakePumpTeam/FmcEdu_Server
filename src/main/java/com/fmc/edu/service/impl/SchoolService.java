package com.fmc.edu.service.impl;

import com.fmc.edu.model.course.Course;
import com.fmc.edu.model.course.TimeTable;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by silly on 2015/5/10.
 */
@Service("schoolService")
public class SchoolService {

	@Resource(name = "schoolRepository")
	private ISchoolRepository mSchoolRepository;

	public Map<String, Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
		return getSchoolRepository().querySchoolsPage(pPagination, pCityId, pKey);
	}

	public List querySchools(final int pCityId, final String pKey) {
		return getSchoolRepository().querySchools(pCityId, pKey);
	}

	public Map<String, Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
		return getSchoolRepository().queryClassesPage(pPagination, pSchoolId, pKey);
	}

	public FmcClass queryDefaultClassBySchoolId(int pSchoolId) {
		return getSchoolRepository().queryDefaultClassBySchoolId(pSchoolId);
	}

	public Map<String, Object> queryHeadmasterPage(final int pClassId) {
		return getSchoolRepository().queryHeadmasterPage(pClassId);
	}

	public boolean updateStudentById(final Student pStudent) {
		return getSchoolRepository().updateStudentById(pStudent);
	}

	public int queryStudentIdByFields(final Student pStudent) {
		return getSchoolRepository().queryStudentIdByFields(pStudent);
	}

	public boolean saveOrUpdateStudentByFields(final Student pStudent) {
		int id = getSchoolRepository().queryStudentIdByFields(pStudent);
		if (id > 0) {
			pStudent.setId(id);
			return updateStudentById(pStudent);
		}
		return getSchoolRepository().initialStudent(pStudent);
	}

	public TeacherProfile queryTeacherById(final int pTeacherId) {
		return getSchoolRepository().queryTeacherById(pTeacherId);
	}

	public List<Course> queryCourseListByClassId(int pClassId, int pWeek) {
		return getSchoolRepository().queryCourseListByClassId(pClassId, pWeek);
	}

	public int insertTimeTable(TimeTable pTimeTable) {
		return getSchoolRepository().insertTimeTable(pTimeTable);
	}

	public int insertCourse(Course pCourse) {
		return getSchoolRepository().insertCourse(pCourse);
	}

	public int updateCourse(Course pCourse) {
		return getSchoolRepository().updateCourse(pCourse);
	}

	public List<BaseProfile> queryAllParentByClassId(int pClassId) {
		return getSchoolRepository().queryAllParentByClassId(pClassId);
	}

	public ISchoolRepository getSchoolRepository() {
		return mSchoolRepository;
	}

	public void setSchoolRepository(ISchoolRepository schoolRepository) {
		this.mSchoolRepository = schoolRepository;
	}


	public School loadSchool(final int pSchoolId) {
		return getSchoolRepository().loadSchool(pSchoolId);
	}

	public boolean updateSchool(final School pSchool) {
		return getSchoolRepository().updateSchool(pSchool);
	}

	public boolean createSchool(final School pSchool) {
		return getSchoolRepository().createSchool(pSchool);
	}

	public List<FmcClass> queryClassesBySchoolId(final int pSchoolId) {
		return getSchoolRepository().queryClassesBySchoolId(pSchoolId);
	}

	public FmcClass loadClass(final int pClassId) {
		return getSchoolRepository().loadClass(pClassId);
	}

	public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pClassId) {
		return getSchoolRepository().queryTeacherClassRelationships(pClassId);
	}

	public boolean updateFmcClass(final FmcClass pFmcClass) {
		return getSchoolRepository().updateFmcClass(pFmcClass);
	}

	public boolean createFmcClass(final FmcClass pFmcClass) {
		return getSchoolRepository().createFmcClass(pFmcClass);
	}
}