package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
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
	public String publishNews(HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam(value = "newsType", required = true) int newsType,
			@RequestParam(value = "subject", required = true) String subject,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs) throws IOException {
		ResponseBean responseBean = new ResponseBean(pRequest);
		if (StringUtils.isBlank(content)) {
			LOG.debug("Content is empty.");
			return "Content is empty.";
		}

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		BaseProfile userProfile = (BaseProfile) session.getAttribute(MyAccountManager.CURRENT_SESSION_USER_KEY);

		News news = new News();
		news.setAuthor(userProfile.getId());
		news.setContent(content);
		news.setNewsType(newsType);
		news.setSubject(subject);
		news.setApproved(true);
		TransactionStatus txStatus = ensureTransaction();
		try {
			if (getNewsManager().insertNews(news)) {
				// int newsId = getNewsManager().queryLastInsertNewsTypeNewsIdByAuthor(userProfile.getId(), newsType);
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
	public String toNewsList(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String mode, String optionId) {
		if (StringUtils.isBlank(optionId)) {
			return "admin/news/news-list";
		}
		int modeInt = Integer.valueOf(mode);
		int classId = Integer.valueOf(optionId);
		if (modeInt == 2 || modeInt == 3 || modeInt == 4 || modeInt == 7) {
			// it's school id actually
			classId = getSchoolManager().queryDefaultClassBySchoolId(classId).getSchoolId();
		}
		Pagination pagination = buildPagination(pRequest);
		List<News> newsList = getNewsManager().queryNewsListByClassId(modeInt, classId, pagination);

		pModel.addAttribute("newsList", newsList);
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
