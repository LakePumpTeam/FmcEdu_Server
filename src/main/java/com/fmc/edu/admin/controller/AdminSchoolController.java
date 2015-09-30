package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.manager.StudentManager;
import com.fmc.edu.manager.TeacherManager;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Yove on 8/14/2015.
 */
@RequestMapping(value = "/admin/school")
@Controller
public class AdminSchoolController extends AdminTransactionBaseController {

	private static final Logger LOG = Logger.getLogger(AdminSchoolController.class);

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;

	@Resource(name = "teacherManager")
	private TeacherManager mTeacherManager;

	@Resource(name = "studentManager")
	private StudentManager mStudentManager;

	@Resource(name = "profileManager")
	private ProfileManager mProfileManager;

	@RequestMapping(value = "/school-list" + GlobalConstant.URL_SUFFIX)
	public String forwardSchoolList(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String cityId) throws UnsupportedEncodingException {
		// initialization situation
		if (StringUtils.isBlank(cityId)) {
			TreeMap locationMap = (TreeMap) pRequest.getServletContext().getAttribute("locationMap");
			if (MapUtils.isNotEmpty(locationMap)) {
				HashMap provinceMap = (HashMap) locationMap.firstEntry().getValue();
				TreeMap cityMap = (TreeMap) provinceMap.get("cities");
				if (MapUtils.isNotEmpty(cityMap)) {
					cityId = String.valueOf(cityMap.firstEntry().getKey());
				}
			}
		}

		int cityIdInt = RepositoryUtils.safeParseId(cityId);
		if (RepositoryUtils.idIsValid(cityIdInt)) {
			List schools = getSchoolManager().querySchools(cityIdInt, StringUtils.EMPTY);
			pModel.addAttribute("schools", schools);
		}
		// pModel.addAttribute("originalURI", getCurrentRequestURIWithParameters(pRequest).toString());
		return "admin/school/school-list";
	}

	@RequestMapping(value = "/school-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardSchoolDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String schoolId) {
		int schoolIdInt = RepositoryUtils.safeParseId(schoolId);
		if (RepositoryUtils.idIsValid(schoolIdInt)) {
			School school = getSchoolManager().loadSchool(schoolIdInt);
			pModel.addAttribute("school", school);
			List<FmcClass> classes = getSchoolManager().queryClassesBySchoolId(schoolIdInt);
			pModel.addAttribute("classes", classes);
			List<TeacherProfile> teachers = getTeacherManager().queryTeachersBySchoolId(schoolIdInt);
			pModel.addAttribute("teachers", teachers);
		}
		return "admin/school/school-detail";
	}

	@RequestMapping(value = "/school-detail-save" + GlobalConstant.URL_SUFFIX)
	public String saveSchoolDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, School school) {
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getSchoolManager().persistSchool(school);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:school-detail?schoolId=" + school.getId();
	}

	@RequestMapping(value = "/teacher-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardTeacherDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String
			teacherId, String schoolId) {
		int teacherIdInt = RepositoryUtils.safeParseId(teacherId);
		if (RepositoryUtils.idIsValid(teacherIdInt)) {
			TeacherProfile teacher = getTeacherManager().queryTeacherById(teacherIdInt);
			pModel.addAttribute("teacher", teacher);
			List<TeacherClassRelationship> relationships = getTeacherManager().queryTeacherClassRelationships(teacherIdInt);
			pModel.addAttribute("relationships", relationships);
		}
		int schoolIdInt = RepositoryUtils.safeParseId(schoolId);
		if (RepositoryUtils.idIsValid(schoolIdInt)) {
			School school = getSchoolManager().loadSchool(schoolIdInt);
			pModel.addAttribute("school", school);
		}
		return "admin/school/teacher-detail";
	}

	@RequestMapping(value = "/teacher-detail-save" + GlobalConstant.URL_SUFFIX)
	public String saveTeacherDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, TeacherProfile teacher) {
		TransactionStatus status = ensureTransaction();
		try {
			teacher.convertBirth();
			boolean result = getTeacherManager().persistTeacherDetail(teacher);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:teacher-detail?teacherId=" + teacher.getId();
	}

	@RequestMapping(value = "/class-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardClassDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String classId) {
		int classIdInt = RepositoryUtils.safeParseId(classId);
		if (RepositoryUtils.idIsValid(classIdInt)) {
			FmcClass fmcClass = getSchoolManager().loadClass(classIdInt);
			if (fmcClass != null) {
				pModel.addAttribute("fmcClass", fmcClass);
				List<TeacherClassRelationship> teacherClassRelationships = getSchoolManager().queryTeacherClassRelationships(classIdInt, -1);
				pModel.addAttribute("relationships", teacherClassRelationships);
				List<Student> students = getStudentManager().loadClassStudents(classIdInt);
				pModel.addAttribute("students", students);
				List<TeacherProfile> noRelTeachers = getTeacherManager().queryTeacherNotInClass(classIdInt);
				pModel.addAttribute("noRelTeachers", noRelTeachers);
			}
		}
		return "admin/school/class-detail";
	}

	@RequestMapping(value = "/class-detail-save" + GlobalConstant.URL_SUFFIX)
	public String saveClassDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, FmcClass fmcClass) {
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getSchoolManager().persistFmcClass(fmcClass);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:class-detail?classId=" + fmcClass.getId();
	}

	@RequestMapping(value = "/class-teacher-rel-save" + GlobalConstant.URL_SUFFIX)
	public String saveClassTeacherRelationship(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel,
			TeacherClassRelationship rel) {
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getSchoolManager().persistClassTeacherRelationship(rel, Boolean.TRUE);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:class-detail?classId=" + rel.getClassId();
	}

	@RequestMapping(value = "/class-teacher-rel-batch-save" + GlobalConstant.URL_SUFFIX)
	public String saveClassTeacherRelationshipBatch(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel,
			String classId, String[] teacherIds, Boolean[] available) {
		int classIdInt = RepositoryUtils.safeParseId(classId);
		if (RepositoryUtils.idIsValid(classIdInt)) {
			TeacherClassRelationship[] relationships = new TeacherClassRelationship[teacherIds.length];
			for (int i = 0; i < teacherIds.length; i++) {
				int teacherIdInt = RepositoryUtils.safeParseId(teacherIds[i]);
				boolean availableVal = available[i] == null ? false : available[i].booleanValue();
				TeacherClassRelationship rel = new TeacherClassRelationship(teacherIdInt, classIdInt, availableVal);
				relationships[i] = rel;
			}
			TransactionStatus status = ensureTransaction();
			try {
				boolean result = getSchoolManager().updateTeacherClassRelationshipAvailableBatch(relationships);
				if (!result) {
					status.setRollbackOnly();
				}
			} catch (Exception e) {
				LOG.error(e);
				status.setRollbackOnly();
			} finally {
				getTransactionManager().commit(status);
			}
		}
		return "redirect:class-detail?classId=" + classId;
	}

	@RequestMapping(value = "/student-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardStudentDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String studentId) {
		int studentIdInt = RepositoryUtils.safeParseId(studentId);
		if (RepositoryUtils.idIsValid(studentIdInt)) {
			Student student = getStudentManager().queryStudentById(studentIdInt);
			pModel.addAttribute("student", student);
			School school = getSchoolManager().loadSchool(student.getFmcClass().getSchoolId());
			pModel.addAttribute("school", school);
			List<ParentStudentRelationship> relationships = getStudentManager().queryParentStudentRelationshipByStudentId(studentIdInt);
			if (CollectionUtils.isNotEmpty(relationships)) {
				Set<ParentProfile> parents = new HashSet<>(relationships.size());
				for (ParentStudentRelationship rel : relationships) {
					ParentProfile parent = getProfileManager().queryParentDetailById(rel.getParentId());
					parent.setParentStudentRelationship(rel);
					parents.add(parent);
				}
				pModel.addAttribute("parents", parents);
			}
		}
		return "admin/school/student-detail";
	}

	@RequestMapping(value = "/student-detail-save" + GlobalConstant.URL_SUFFIX)
	public String saveStudentDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, Student student) {
		TransactionStatus status = ensureTransaction();
		try {
			student.convertBirth();
			boolean result = getStudentManager().persistStudent(student);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:student-detail?studentId=" + student.getId();
	}

	@RequestMapping(value = "/student-parent-save" + GlobalConstant.URL_SUFFIX)
	public String saveParentDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, ParentProfile parent) {
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getProfileManager().persistParentDetail(parent);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "redirect:student-detail?studentId=" + parent.getParentStudentRelationship().getStudentId();
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}

	public TeacherManager getTeacherManager() {
		return mTeacherManager;
	}

	public void setTeacherManager(final TeacherManager pTeacherManager) {
		mTeacherManager = pTeacherManager;
	}

	public StudentManager getStudentManager() {
		return mStudentManager;
	}

	public void setStudentManager(final StudentManager pStudentManager) {
		mStudentManager = pStudentManager;
	}

	public ProfileManager getProfileManager() {
		return mProfileManager;
	}

	public void setProfileManager(final ProfileManager pProfileManager) {
		mProfileManager = pProfileManager;
	}
}