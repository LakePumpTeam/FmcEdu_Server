package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.cache.impl.newslike.NewsLikeCacheContent;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.exception.NewsException;
import com.fmc.edu.exception.ProfileException;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Controller
@RequestMapping("/news")
public class NewsActivityController extends BaseController {

    public static Object WRITE_FILE_LOCK = "writeFileLock";
    public static Object LIKE_NEWS_LOCK = "likeNewsLock";

    private static final Logger LOG = Logger.getLogger(NewsActivityController.class);

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

        TransactionStatus txStatus = ensureTransaction();
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

            int newsType = Integer.valueOf(typeStr);
            List<News> newsList = getNewsManager().queryNewsListByNewType(pagination, newsType);
            responseBean.addData(JSONOutputConstant.IS_LAST_PAGE, RepositoryUtils.isLastPageFlag(newsList, pagination.getPageSize()));

            if (CollectionUtils.isEmpty(newsList)) {
                return output(responseBean);
            }

            //Should create a new BaseProfile instance, otherwise maybe update error data to database
            BaseProfile updateProfile = new BaseProfile();
            updateProfile.setId(currentUser.getId());
            int maxTypeNewsId = getNewsManager().queryNewsMaxIdByNewsType(newsType);
            LOG.debug("Got the max id:" + maxTypeNewsId + " of news:" + newsType);
            switch (newsType) {
                case NewsType.SCHOOL_DYNAMICS_ACTIVITY: {
                    updateProfile.setLastSDATId(maxTypeNewsId);
                    break;
                }
                case NewsType.SCHOOL_DYNAMICS_NEWS: {
                    updateProfile.setLastSDNWId(maxTypeNewsId);
                    break;
                }
                case NewsType.SCHOOL_DYNAMICS_NOTIFICATION: {
                    updateProfile.setLastSDNFId(maxTypeNewsId);
                    break;
                }
                case NewsType.CLASS_DYNAMICS: {
                    updateProfile.setLastCLId(maxTypeNewsId);
                    break;
                }
                case NewsType.PARENTING_CLASS: {
                    updateProfile.setLastPCId(maxTypeNewsId);
                    break;
                }
                case NewsType.PARENT_CHILD_EDU: {
                    updateProfile.setLastPCEId(maxTypeNewsId);
                    break;
                }
                case NewsType.SCHOOL_BBS: {
                    updateProfile.setLastBBSId(maxTypeNewsId);
                    break;
                }
            }
            getMyAccountManager().updateBaseProfile(updateProfile);

            // update like number from cache
            if (CollectionUtils.isNotEmpty(newsList)) {
                NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
                for (News news : newsList) {
                    int cachedNewsLike = (int) cacheContent.getCachedValue(String.valueOf(news.getId()), news.getLike());
                    news.setLike(cachedNewsLike);
                }
            }
            getResponseBuilder().buildNewsListResponse(responseBean, newsList, currentUser);
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping("/requestSlides")
    @ResponseBody
    public String requestSlides(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean();
        try {
            //TODO need to confirm the date period
            List<Slide> slideList = getNewsManager().querySlideList(DateUtils.getDaysLater(-1), DateUtils.getDaysLater(30));
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
                responseBean.addBusinessMsg("用户Id错误.");
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg("新闻ID错误:" + newsIdStr);
                return output(responseBean);
            }
            int userId = Integer.valueOf(userIdStr);
            int newsId = Integer.valueOf(newsIdStr);

            News news = getNewsManager().queryNewsDetail(newsId);
            if (news == null) {
                LOG.debug("Can not find any news detail for news id:" + newsIdStr);
                return output(responseBean);
            }
            // update like number from cache
            NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
            int cachedNewsLike = (int) cacheContent.getCachedValue(String.valueOf(news.getId()), news.getLike());
            news.setLike(cachedNewsLike);

            List<Comments> commentsList = getNewsManager().queryCommentsByNewsIdAndProfileId(userId, newsId);
            getResponseBuilder().buildNewsDetailResponse(responseBean, news, commentsList, userId);
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
                responseBean.addBusinessMsg("User id is invalid.");
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg("News id is invalid:" + newsIdStr);
                return output(responseBean);
            }
            if (StringUtils.isBlank(contentStr)) {
                responseBean.addBusinessMsg("News id is invalid:" + contentStr);
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
        TransactionStatus txStatus = ensureTransaction();
        try {
            String userIdStr = decodeInput(pRequest.getParameter("userId"));
            String newsIdStr = decodeInput(pRequest.getParameter("newsId"));
            String isLikeStr = decodeInput(pRequest.getParameter("isLike"));
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                throw new NewsException("用户ID错误:" + userIdStr);
            }
            if (!StringUtils.isNumeric(newsIdStr)) {
                throw new NewsException("文章ID错误:" + newsIdStr);
            }
            boolean isLike = true;
            if (!StringUtils.isBlank(isLikeStr)) {
                isLike = Boolean.valueOf(isLikeStr);
            }
            int profileId = Integer.valueOf(userIdStr);
            int newsId = Integer.valueOf(newsIdStr);

            Map<String, Object> profileNewsRelation = getMyAccountManager().queryLikeNewsRelation(profileId, newsId);
            if (isLike) {
                //relation is not exist in database, then insert the relation for like request
                if (profileNewsRelation == null || profileNewsRelation.size() == 0) {
                    getMyAccountManager().addLikeNewsRelation(profileId, newsId);
                }
            } else if (profileNewsRelation != null) {
                getMyAccountManager().deleteLikeNewsRelation(profileId, newsId);
            } else {
                throw new NewsException("状态错误.");
            }

            // handle cache
            NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
            Map<String, Object> params = new HashMap<>();
            params.put(CacheContent.UPDATE_TYPE, isLike ? NewsLikeCacheContent.PLUS_ONE : NewsLikeCacheContent.MINUS_ONE);
            cacheContent.updateCache(String.valueOf(newsId), params);

            //TODO integration with memory cache for like numbers
            return output(responseBean);
        } catch (NewsException ex) {
            responseBean.addBusinessMsg(ex.getMessage());
            txStatus.setRollbackOnly();
            LOG.error(ex);
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
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
            responseBean.addData(getNewsManager().getReadNewsStatus(Integer.valueOf(userIdStr)));
            return output(responseBean);
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage());
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
                                @RequestParam(value = "userId", required = true) String userId,
                                @RequestParam(value = "subject", required = false) String subject,
                                @RequestParam(value = "content", required = true) String content,
                                @RequestParam(value = "imgs", required = false) MultipartFile[] imgs
    ) {
        ResponseBean responseBean = new ResponseBean();

        TransactionStatus txStatus = ensureTransaction();
        try {
            String userIdStr = decodeInput(userId);
            //String subjectStr = decodeInput(subject);
            String contentStrStr = decodeInput(content);
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg("用户ID 为空.");
                throw new Exception("用户ID 为空.");
            }
            /*if (!StringUtils.isBlank(subjectStr)) {
                responseBean.addBusinessMsg("" + subjectStr);
				return output(responseBean);
			}*/
            if (StringUtils.isBlank(contentStrStr)) {
                responseBean.addBusinessMsg("内容不能为空.");
                throw new Exception("内容不能为空.");
            }
            int userIdInt = Integer.valueOf(userIdStr);
            News classNews = new News();
            classNews.setAuthor(userIdInt);
            classNews.setContent(contentStrStr);
            classNews.setNewsType(NewsType.CLASS_DYNAMICS);
            classNews.setSubject(StringUtils.EMPTY);
            classNews.setApproved(true);
            if (!getNewsManager().insertNews(classNews)) {
                responseBean.addBusinessMsg("发布班级动态失败.");
                throw new Exception("发布班级动态失败.");
            }
            // int newsId = getNewsManager().queryLastInsertNewsTypeNewsIdByAuthor(userIdInt, NewsType.CLASS_DYNAMICS);

            getNewsManager().saveNewsImage(imgs, userIdStr, classNews.getId());

            return output(responseBean);
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e.fillInStackTrace());
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
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
