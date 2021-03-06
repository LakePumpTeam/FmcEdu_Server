package com.fmc.edu.manager;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.course.Course;
import com.fmc.edu.model.course.TimeTable;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.impl.LocationService;
import com.fmc.edu.service.impl.SchoolService;
import com.fmc.edu.service.impl.TeacherService;
import com.fmc.edu.util.NumberUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "schoolManager")
public class SchoolManager {

	@Resource(name = "schoolService")
	private SchoolService mSchoolService;

	@Resource(name = "locationService")
	private LocationService mLocationService;

	@Resource
	private TeacherService mTeacherService;

	public Map<String, Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
		return getSchoolService().querySchoolsPage(pPagination, pCityId, pKey);
	}


	public List querySchools(int pCityId, String pKey) {
		return getSchoolService().querySchools(pCityId, pKey);
	}

	public Map<String, Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
		Map<String, Object> classes = getSchoolService().queryClassesPage(pPagination, pSchoolId, pKey);
		List<Map<String, String>> queryResult = (List<Map<String, String>>) classes.get("classList");
		for (Map<String, String> res : queryResult) {
			res.put("className", getClassString(String.valueOf(res.get("grade")), String.valueOf(res.get("class"))));
		}
		return classes;
	}

	public String getClassString(String grade, String cls) {
		if (StringUtils.isBlank(grade) || StringUtils.isBlank(cls)) {
			return "";
		}
		int year = Integer.valueOf(grade);
		Calendar calendar = Calendar.getInstance();
		int gradeY = calendar.get(Calendar.YEAR) - year;
		int month = calendar.get(Calendar.MONTH);
		if (month > 7) {
			gradeY++;
		}
		if (gradeY == 0) {
			gradeY++;
		}
		StringBuilder convertedClass = new StringBuilder();
		convertedClass.append(NumberUtils.numberToChineseNumber(gradeY)).append("年级").append(NumberUtils.numberToChineseNumber(Integer.valueOf(cls)))
				.append("班");
		return convertedClass.toString();
	}

	public Map<String, Object> queryHeadmasterPage(final int pClassId) {
		return getSchoolService().queryHeadmasterPage(pClassId);
	}

	public FmcClass queryDefaultClassBySchoolId(int pSchoolId) {
		return getSchoolService().queryDefaultClassBySchoolId(pSchoolId);
	}

	public List<FmcClass> queryClassesBySchoolId(int pSchoolId) {
		return getSchoolService().queryClassesBySchoolId(pSchoolId);
	}

	public boolean persistStudent(final Student pStudent) {
		if (RepositoryUtils.idIsValid(pStudent.getId())) {
			return getSchoolService().updateStudentById(pStudent);
		}
		return getSchoolService().saveOrUpdateStudentByFields(pStudent);
	}

	public int queryStudentIdByFields(final Student pStudent) {
		return getSchoolService().queryStudentIdByFields(pStudent);
	}

	public TeacherProfile queryTeacherById(final int pTeacherId) {
		return getSchoolService().queryTeacherById(pTeacherId);
	}

	public List<Course> queryCourseListByClassId(int pClassId, int pWeek) {
		return getSchoolService().queryCourseListByClassId(pClassId, pWeek);
	}

	public int insertTimeTable(TimeTable pTimeTable) {
		return getSchoolService().insertTimeTable(pTimeTable);
	}

	public int insertCourse(Course pCourse) {
		return getSchoolService().insertCourse(pCourse);
	}

	public int updateCourse(Course pCourse) {
		return getSchoolService().updateCourse(pCourse);
	}

	public Set<BaseProfile> queryAllParentByClassId(int pClassId) {
		Set<BaseProfile> allParentId = new HashSet<BaseProfile>(getSchoolService().queryAllParentByClassId(pClassId));
		return allParentId;
	}

	public School loadSchool(final int pSchoolId) {
		return getSchoolService().loadSchool(pSchoolId);
	}

	public boolean persistSchool(final School pSchool) {
		boolean result;
		Address schoolAddress = pSchool.extractAddress();
		if (RepositoryUtils.idIsValid(schoolAddress.getId())) {
			result = getLocationService().updateAddress(schoolAddress);
		} else if (StringUtils.isNoneBlank(schoolAddress.getAddress())) {
			result = getLocationService().createAddress(schoolAddress);
			pSchool.setAddressId(schoolAddress.getId());
		} else {
			result = true;
		}
		if (result) {
			if (RepositoryUtils.idIsValid(pSchool.getId())) {
				result = getSchoolService().updateSchool(pSchool);
			} else {
				result = getSchoolService().createSchool(pSchool);
			}
		}
		return result;
	}


	public FmcClass loadClass(int pClassId) {
		return getSchoolService().loadClass(pClassId);
	}

	public boolean persistFmcClass(final FmcClass pFmcClass) {
		if (RepositoryUtils.idIsValid(pFmcClass.getId())) {
			boolean result = getSchoolService().updateFmcClass(pFmcClass);
			if (result) {
				result = getTeacherService().maintainHeadTeacherRelationship(pFmcClass.getId(), pFmcClass.getHeadTeacherId());
			}
			return result;
		} else {
			return getSchoolService().createFmcClass(pFmcClass) > 0;
		}
	}


	public int createFmcClass(final FmcClass pFmcClass) {
		return getSchoolService().createFmcClass(pFmcClass);
	}

	public School querySchoolByFields(Map<String, Object> pFields) {
		return getSchoolService().querySchoolByFields(pFields);
	}

	public Map<String, Object> queryHeadmasterByClassAndSchool(Map<String, Object> pMap) {
		return getSchoolService().queryHeadmasterByClassAndSchool(pMap);
	}

	public boolean persistClassTeacherRelationship(final TeacherClassRelationship pRel, final boolean pUpdateHeadTeacher) {
		List<TeacherClassRelationship> rels = queryTeacherClassRelationships(pRel.getClassId(), pRel.getTeacherId());
		boolean result;
		if (CollectionUtils.isEmpty(rels)) {
			result = getTeacherService().createTeacherClassRelationship(pRel) > 0;
		} else {
			result = getTeacherService().updateTeacherClassRelationship(pRel);
			if (pRel.isHeadTeacher()) {
				result = getTeacherService().maintainHeadTeacherRelationship(pRel.getClassId(), pRel.getTeacherId());
			}
		}
		if (pUpdateHeadTeacher && result && pRel.isHeadTeacher()) {
			result = getTeacherService().maintainHeadTeacherRelationship(pRel.getClassId(), pRel.getTeacherId());
		}
		return result;
	}

	public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pClassId, int pTeacherId) {
		return getSchoolService().queryTeacherClassRelationships(pClassId, pTeacherId);
	}

	public boolean updateTeacherClassRelationshipAvailableBatch(final TeacherClassRelationship[] pRelationships) {
		return getTeacherService().updateTeacherClassRelationshipAvailableBatch(pRelationships);
	}

	public LocationService getLocationService() {
		return mLocationService;
	}

	public void setLocationService(final LocationService pLocationService) {
		mLocationService = pLocationService;
	}

	public TeacherService getTeacherService() {
		return mTeacherService;
	}

	public void setTeacherService(TeacherService pTeacherService) {
		mTeacherService = pTeacherService;
	}

	public SchoolService getSchoolService() {
		return mSchoolService;
	}

	public void setSchoolService(SchoolService pSchoolService) {
		this.mSchoolService = pSchoolService;
	}
}
