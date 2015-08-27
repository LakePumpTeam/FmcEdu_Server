package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.manager.TeacherManager;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

	@RequestMapping(value = "/school-list" + GlobalConstant.URL_SUFFIX)
	public String forwardSchoolList(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String cityId) throws UnsupportedEncodingException {
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
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
		}
		return "admin/school/school-detail";
	}

	@RequestMapping(value = "/teacher-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardTeacherDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String teacherId) {
		int teacherIdInt = RepositoryUtils.safeParseId(teacherId);
		if (RepositoryUtils.idIsValid(teacherIdInt)) {
			TeacherProfile teacher = getTeacherManager().queryTeacherById(teacherIdInt);
			pModel.addAttribute("teacher", teacher);
			List<TeacherClassRelationship> relationships = getTeacherManager().queryTeacherClassRelationships(teacherIdInt);
			pModel.addAttribute("relationships", relationships);
		}
		return "admin/school/teacher-detail";
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
}