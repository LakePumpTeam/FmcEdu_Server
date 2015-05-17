package com.fmc.edu.web.controller;

import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
@Controller
@RequestMapping("/school")
public class SchoolController extends BaseController {
	private static final Logger LOG = Logger.getLogger(SchoolController.class);

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;

	@RequestMapping("/requestSchools")
	@ResponseBody
	public String requestSchools(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cityId, final String filterKey) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String city = decodeInput(cityId);
			String filter = decodeInput(filterKey);
			Pagination pagination = buildPagination(pRequest);

			Map<String, Object> schools = getSchoolManager().querySchoolsPage(pagination, Integer.valueOf(city), filter);
			responseBean.addData(schools);
		} catch (IOException e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		} catch (Exception ex) {
			responseBean.addErrorMsg(ex);
			LOG.error(ex);
		}
		return responseBean.toString();
	}

	@RequestMapping("/requestClasses")
	@ResponseBody
	public String requestClasses(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String schoolId, final String filterKey) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String school = decodeInput(schoolId);
			String filter = decodeInput(filterKey);
			Pagination pagination = buildPagination(pRequest);

			Map<String, Object> classses = getSchoolManager().queryClassesPage(pagination, Integer.valueOf(school), filter);
			responseBean.addData(classses);
		} catch (IOException e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		} catch (Exception ex) {
			responseBean.addErrorMsg(ex);
			LOG.error(ex);
		}
		return responseBean.toString();
	}


	@RequestMapping("/requestHeadTeacher")
	@ResponseBody
	public String requestHeadmaster(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String classId) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String correspondingClass = decodeInput(classId);

			Map<String, Object> headmaster = getSchoolManager().queryHeadmasterPage(Integer.valueOf(correspondingClass));
			responseBean.addData(headmaster);
		} catch (IOException e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		} catch (Exception ex) {
			responseBean.addErrorMsg(ex);
			LOG.error(ex);
		}
		return responseBean.toString();
	}

	@RequestMapping("/requestTeacherInfo")
	@ResponseBody
	public String requestTeacherInfo(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String teacherId) throws IOException {
		ResponseBean responseBean = new ResponseBean();
		int tid = Integer.valueOf(decodeInput(teacherId));
		TeacherProfile teacher = getSchoolManager().queryTeacherById(tid);
		responseBean.addData("teacherName", teacher.getName());
		responseBean.addData("teacherBirth", DateUtils.getStudentBirthString(teacher.getBirth()));
		responseBean.addData("cellPhone", teacher.getPhone());
		responseBean.addData("course", teacher.getCourse());
		responseBean.addData("resume", teacher.getResume());
		responseBean.addData("teacherSex", teacher.isMale());
		return responseBean.toString();
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(SchoolManager pSchoolManager) {
		this.mSchoolManager = pSchoolManager;
	}
}
