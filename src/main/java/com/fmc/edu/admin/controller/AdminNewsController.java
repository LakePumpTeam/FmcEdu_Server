package com.fmc.edu.admin.controller;

import com.fmc.edu.admin.builder.SelectPaginationBuilder;
import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.news.FmcNewsType;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.model.news.Selection;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 5/28/2015.
 */
@Controller
@RequestMapping("/admin/news")
public class AdminNewsController extends AdminTransactionBaseController {
	private static final Logger LOG = Logger.getLogger(AdminNewsController.class);

	@Resource(name = "newsManager")
	private NewsManager mNewsManager;

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;

	@RequestMapping(value = "/news-publish" + GlobalConstant.URL_SUFFIX)
	public String toNewsPublish(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		return "admin/news/news-publish";
	}

	@RequestMapping(value = "/publishNews" + GlobalConstant.URL_SUFFIX, method = RequestMethod.POST)
	@ResponseBody
	public String publishNews(HttpServletRequest pRequest, HttpServletResponse pResponse, int newsType, String subject, String content,
			@RequestParam(value = "schoolId", required = false, defaultValue = "0") int schoolId,
			@RequestParam(value = "classId", required = false, defaultValue = "0") int classId,
			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs,
			@RequestParam(value = "selections", required = false) String[] selections) throws IOException {
		ResponseBean responseBean = new ResponseBean(pRequest);

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		BaseProfile userProfile = (BaseProfile) session.getAttribute(MyAccountManager.CURRENT_SESSION_USER_KEY);

		News news = new News();
		news.setAuthor(userProfile.getId());
		news.setContent(content);
		news.setNewsType(newsType);
		news.setSubject(subject);
		news.setApproved(true);

		if (!FmcNewsType.isGlobalLevel(newsType)) {
			// set reference id for school/class level news
			if (FmcNewsType.isSchoolLevel(newsType)) {
				news.setRefId(schoolId);
			} else if (FmcNewsType.isClassLevel(newsType)) {
				news.setRefId(classId);
			}
			// assemble selections
			if (newsType == NewsType.SCHOOL_BBS) {
				if (ArrayUtils.isEmpty(selections)) {
					throw new IllegalArgumentException("Options for school bbs news should not be empty.");
				}
				List<Selection> selectionList = new ArrayList<>(selections.length);
				int index = 1;
				for (String selection : selections) {
					if (StringUtils.isBlank(selection)) {
						continue;
					}
					selectionList.add(new Selection(selection, index++));
				}
				news.setOptions(selectionList);
			}
		}

		TransactionStatus txStatus = ensureTransaction();
		try {
			if (getNewsManager().insertNews(news)) {
				getNewsManager().saveNewsImage(imgs, String.valueOf(userProfile.getId()), news.getId());
			} else {
				LOG.debug("Publish news failed.");
				responseBean.addBusinessMsg("Publish news failed.");
			}
		} catch (Exception e) {
			LOG.error(e);
			txStatus.setRollbackOnly();
			responseBean.addBusinessMsg(e.getMessage());
		} finally {
			getTransactionManager().commit(txStatus);
		}
		return "Success";
	}


	@RequestMapping(value = "/news-list")
	public String toNewsList(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, int
			mode, String cityId, String schoolId, String classId) {
		if (mode == 1 || StringUtils.isNotBlank(schoolId) || StringUtils.isNotBlank(classId)) {
			int optionalId = 0;
			if (StringUtils.isNoneBlank(classId)) {
				optionalId = Integer.valueOf(classId);
			}
			if (mode == 2 || mode == 3 || mode == 4 || mode == 7) {
				// Fixed the optional id to default class id by school id
				int schoolIdInt = Integer.valueOf(schoolId);
				optionalId = getSchoolManager().queryDefaultClassBySchoolId(schoolIdInt).getSchoolId();
			}
			Pagination pagination = buildPagination(pRequest);
			List<News> newsList = getNewsManager().queryNewsListByClassId(mode, optionalId, pagination);
			pModel.addAttribute("newsList", newsList);
		}
		if (StringUtils.isNoneBlank(cityId)) {
			int cityIdInt = Integer.valueOf(cityId);
			Pagination pagination = SelectPaginationBuilder.getSelectPagination();
			Object schools = getSchoolManager().querySchoolsPage(pagination, cityIdInt, null).get("schools");
			pModel.addAttribute("schools", schools);
		}
		return "admin/news/news-list";
	}

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}
}
