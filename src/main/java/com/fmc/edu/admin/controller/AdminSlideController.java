package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yove on 6/7/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminSlideController extends AdminTransactionBaseController {


	@Resource(name = "newsManager")
	private NewsManager mNewsManager;

	@RequestMapping(value = "/slide" + GlobalConstant.URL_SUFFIX)
	public String toSlide(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel) {
		List<Slide> activeSlides = getNewsManager().querySlideList(true, DateUtils.getDaysLater(-100), DateUtils.getDaysLater(100));
		pModel.addAttribute("activeSlides", activeSlides);
		List<Slide> inactiveSlides = getNewsManager().querySlideList(false, DateUtils.getDaysLater(-100), DateUtils.getDaysLater(100));
		pModel.addAttribute("inactiveSlides", inactiveSlides);
		List<News> newsList = getNewsManager().queryNewsListByNewType(buildDefaultPagination(), NewsType.PARENT_CHILD_EDU);
		pModel.addAttribute("newsList", newsList);
		return "admin/slide/slide";
	}

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(final NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}
}
