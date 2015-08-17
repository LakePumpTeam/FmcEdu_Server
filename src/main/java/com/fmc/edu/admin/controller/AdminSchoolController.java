package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.SchoolManager;
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

	@RequestMapping(value = "/school-list" + GlobalConstant.URL_SUFFIX)
	public String forwardSchoolList(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String cityId) throws UnsupportedEncodingException {
		int cityIdInt = RepositoryUtils.safeParseId(cityId);
		if (RepositoryUtils.idIsValid(cityIdInt)) {
			List schools = getSchoolManager().querySchools(cityIdInt, StringUtils.EMPTY);
			pModel.addAttribute("schools", schools);
		}
		pModel.addAttribute("originalURI", getCurrentRequestURIWithParameters(pRequest).toString());
		return "admin/school/school-list";
	}

	@RequestMapping(value = "/school-detail" + GlobalConstant.URL_SUFFIX)
	public String forwardSchoolDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String schoolId, String back) {
		int schoolIdInt = RepositoryUtils.safeParseId(schoolId);
		if (RepositoryUtils.idIsValid(schoolIdInt)) {
			School school = getSchoolManager().loadSchool(schoolIdInt);
			pModel.addAttribute("school", school);
		}
		pModel.addAttribute("back", back);
		return "admin/school/school-detail";
	}

	@RequestMapping(value = "/school-detail-save" + GlobalConstant.URL_SUFFIX)
	public String saveSchoolDetail(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, School school, String back) {
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
		pModel.addAttribute("back", back);
		return "admin/school/school-detail";
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}
}
