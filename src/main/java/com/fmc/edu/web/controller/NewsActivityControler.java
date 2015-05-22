package com.fmc.edu.web.controller;

import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
@Controller
@RequestMapping("/news")
public class NewsActivityControler extends BaseController {
	private static final Logger LOG = Logger.getLogger(NewsActivityControler.class);

	@Resource(name = "responseBuilder")
	private ResponseBuilder mResponseBuilder;

	@Resource(name = "newsManager")
	private NewsManager mNewsManager;

	@Resource(name = "myAccountManager")
	private MyAccountManager mMyAccountManager;

	@RequestMapping("/requestNewsList")
	@ResponseBody
	public String requestNewsList(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			Pagination pagination = buildPagination(pRequest);
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			String typeStr = decodeInput(pRequest.getParameter("type"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id 不合法.");
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(typeStr)) {
				responseBean.addBusinessMsg("新闻类型错误:" + typeStr);
				return output(responseBean);
			}

			BaseProfile currentUser = getMyAccountManager().findUserById(userIdStr);
			if (currentUser == null) {
				responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
				return output(responseBean);
			}

			int newType = Integer.valueOf(typeStr);
			List<News> newsList = getNewsManager().queryNewsListByNewType(pagination, newType);
			responseBean.addData(JSONOutputConstant.IS_LAST_PAGE, RepositoryUtils.isLastPageFlag(newsList, pagination.getPageSize()));

			if (CollectionUtils.isEmpty(newsList)) {
				return output(responseBean);
			}
			if (newType == NewsType.SCHOOL_DYNAMICS_ACTIVITY.getValue() || newType == NewsType.SCHOOL_DYNAMICS_NEWS.getValue() || newType == NewsType.SCHOOL_DYNAMICS_NOTIFY.getValue()) {
				currentUser.setLastSCId(getNewsManager().queryNewsMaxIdByNewsType(newType));
			} else if (newType == NewsType.CLASS_DYNAMICS.getValue()) {
				currentUser.setLastCLId(getNewsManager().queryNewsMaxIdByNewsType(newType));
			} else if (newType == NewsType.PARENTING_CLASS.getValue()) {
				currentUser.setLastPCId(getNewsManager().queryNewsMaxIdByNewsType(newType));
			}
			//TODO update base profile
			//getMyAccountManager().
			getResponseBuilder().buildNewsListResponse(responseBean, newsList);

		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping("/requestSlides")
	@ResponseBody
	public String requestSlides(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			//TODO need to confirm the date period
			List<Slide> slideList = getNewsManager().querySlideList(DateUtils.getDaysLater(-1), DateUtils.getDaysLater(1));
			getResponseBuilder().buildSlideListResponse(responseBean, slideList);
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping("/requestNewsDetail")
	@ResponseBody
	public String requestNewsDetail(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String newsIdStr = decodeInput(pRequest.getParameter("newsId"));
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id .");
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(newsIdStr)) {
				responseBean.addBusinessMsg("" + newsIdStr);
				return output(responseBean);
			}
			//TODO .....
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping("/postComment")
	@ResponseBody
	public String postComment(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String newsIdStr = decodeInput(pRequest.getParameter("newsId"));
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			String contentStr = decodeInput(pRequest.getParameter("content"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id .");
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(newsIdStr)) {
				responseBean.addBusinessMsg("" + newsIdStr);
				return output(responseBean);
			}
			if (!StringUtils.isBlank(contentStr)) {
				responseBean.addBusinessMsg("" + newsIdStr);
				return output(responseBean);
			}
			Comments comments = new Comments();
			comments.setNewsId(Integer.valueOf(newsIdStr));
			comments.setProfileId(Integer.valueOf(userIdStr));
			comments.setComment(contentStr);

			getNewsManager().insertComment(comments);
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping("/likeNews")
	@ResponseBody
	public String likeNews(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			String newsIdStr = decodeInput(pRequest.getParameter("newsId"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id is invalid: " + userIdStr);
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(newsIdStr)) {
				responseBean.addBusinessMsg("News id is invalid: " + newsIdStr);
				return output(responseBean);
			}
			int userId = Integer.valueOf(userIdStr);
			int newsId = Integer.valueOf(newsIdStr);
			getMyAccountManager().likeNews(userId, newsId);
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping("/checkNewNews")
	@ResponseBody
	public String checkNewNews(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id .");
				return output(responseBean);
			}
			//TODO need to implement
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	@RequestMapping(value = "/postClassNews", method = RequestMethod.POST)
	@ResponseBody
	public String postClassNews(final HttpServletRequest pRequest,
			final HttpServletResponse pResponse,
			@RequestParam("img1") MultipartFile img1,
			@RequestParam("img2") MultipartFile img2,
			@RequestParam("img3") MultipartFile img3,
			@RequestParam("img4") MultipartFile img4
	) {
		ResponseBean responseBean = new ResponseBean();
		try {
			String userIdStr = decodeInput(pRequest.getParameter("userId"));
			String newsIdStr = decodeInput(pRequest.getParameter("subject"));
			String contentStr = decodeInput(pRequest.getParameter("content"));
			if (!RepositoryUtils.idIsValid(userIdStr)) {
				responseBean.addBusinessMsg("User id .");
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(newsIdStr)) {
				responseBean.addBusinessMsg("" + newsIdStr);
				return output(responseBean);
			}
			if (!StringUtils.isNumeric(contentStr)) {
				responseBean.addBusinessMsg("" + contentStr);
				return output(responseBean);
			}

			//TODO need to implement
			return output(responseBean);
		} catch (Exception e) {
			responseBean.addErrorMsg(e);
			LOG.error(e);
		}
		return output(responseBean);
	}

	public NewsManager getNewsManager() {
		return mNewsManager;
	}

	public void setNewsManager(NewsManager pNewsManager) {
		mNewsManager = pNewsManager;
	}

	public ResponseBuilder getResponseBuilder() {
		return mResponseBuilder;
	}

	public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
		mResponseBuilder = pResponseBuilder;
	}

	public MyAccountManager getMyAccountManager() {
		return mMyAccountManager;
	}

	public void setMyAccountManager(MyAccountManager pMyAccountManager) {
		mMyAccountManager = pMyAccountManager;
	}
}
