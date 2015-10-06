package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yove on 6/7/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminSlideController extends AdminTransactionBaseController {

	private static final Logger LOG = Logger.getLogger(AdminSlideController.class);

	@Resource(name = "newsManager")
	private NewsManager mNewsManager;

	@RequestMapping(value = "/slide" + GlobalConstant.URL_SUFFIX)
	public String toSlide(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel) {
		List<Slide> activeSlides = getNewsManager().querySlideList(true, DateUtils.getDaysLater(-100), DateUtils.getDaysLater(100));
		pModel.addAttribute("activeSlides", activeSlides);
		List<Slide> inactiveSlides = getNewsManager().querySlideList(false, DateUtils.getDaysLater(-100), DateUtils.getDaysLater(100));
		pModel.addAttribute("inactiveSlides", inactiveSlides);
		List<News> newsList = getNewsManager().queryNewsListByClassId(NewsType.PARENTING_CLASS, 0, buildDefaultPagination());
		pModel.addAttribute("newsList", newsList);
		return "admin/slide/slide";
	}

	@RequestMapping(value = "/saveSlides" + GlobalConstant.URL_SUFFIX)
	public String saveSlides(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, int[] ids, int[] order,
			boolean[] available) {
		List<Slide> slides = new ArrayList<>(ids.length);
		for (int i = 0; i < ids.length; i++) {
			Slide sl = new Slide();
			sl.setId(ids[i]);
			sl.setOrder(order[i]);
			sl.setAvailable(available[i]);
			slides.add(sl);
		}
		TransactionStatus ts = ensureTransaction();
		try {
			boolean success = getNewsManager().UpdateSlidesBatch(slides);
			if (!success) {
				ts.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			ts.setRollbackOnly();
		} finally {
			pModel.addAttribute("success", ts.isRollbackOnly() ? 0 : 1);
			getTransactionManager().commit(ts);
		}
		return toSlide(pRequest, pResponse, pModel);
	}

	@RequestMapping(value = "/createSlide" + GlobalConstant.URL_SUFFIX)
	public String createSlide(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel, String subject, Integer newsId,
			Boolean available, MultipartFile image) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		BaseProfile userProfile = (BaseProfile) session.getAttribute(MyAccountManager.CURRENT_SESSION_USER_KEY);

		Slide slide = new Slide();
		slide.setSubject(subject);
		slide.setNewsId(newsId);
		slide.setAvailable(available);
		slide.setProfileId(userProfile.getId());
		slide.setOrder(1);

		TransactionStatus ts = ensureTransaction();
		try {
			boolean success = getNewsManager().createSlide(slide, image);
			if (!success) {
				ts.setRollbackOnly();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			ts.setRollbackOnly();
		} finally {
			pModel.addAttribute("success", ts.isRollbackOnly() ? 0 : 1);
			getTransactionManager().commit(ts);
		}
		return toSlide(pRequest, pResponse, pModel);
	}

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(final NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}
}
