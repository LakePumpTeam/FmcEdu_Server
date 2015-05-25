package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.cache.impl.newslike.NewsLikeCacheContent;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.exception.NewsException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.ImageUtils;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Controller
@RequestMapping("/news")
public class NewsActivityControler extends BaseController {
    public static Object WRITE_FILE_LOCK = "writeFileLock";
    public static Object LIKE_NEWS_LOCK = "likeNewsLock";

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
            responseBean.addData("type", newsType);
            List<News> newsList = getNewsManager().queryNewsListByNewType(pagination, newsType);
            responseBean.addData(JSONOutputConstant.IS_LAST_PAGE, RepositoryUtils.isLastPageFlag(newsList, pagination.getPageSize()));

            if (CollectionUtils.isEmpty(newsList)) {
                return output(responseBean);
            }

            //Should create a new BaseProfile instance, otherwise maybe update error data to database
            BaseProfile updateProfile = new BaseProfile();
            updateProfile.setId(currentUser.getId());
            int maxTypeNewsId = getNewsManager().queryNewsMaxIdByNewsType(newsType);
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

            News newsDetail = getNewsManager().queryNewsDetail(newsId);
            if (newsDetail == null) {
                LOG.debug("Can not find any news detail for news id:" + newsIdStr);
                return output(responseBean);
            }
            List<Comments> commentsList = getNewsManager().queryCommentsByNewsIdAndProfileId(userId, newsId);
            getResponseBuilder().buildNewsDetailResponse(responseBean, newsDetail, commentsList, userId);
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
                                @RequestParam("imgs") MultipartFile[] imgs
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
            int newsId = getNewsManager().queryLastInsertNewsTypeNewsIdByAuthor(userIdInt, NewsType.CLASS_DYNAMICS);

            synchronized (WRITE_FILE_LOCK) {
                if (imgs != null && imgs.length > 0) {
                    for (MultipartFile img : imgs) {
                        saveNewsImage(img, userIdStr, newsId);
                    }
                }
            }
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

    private void saveNewsImage(MultipartFile pImage, String pUserId, int pNewsId) throws IOException {
        LOG.debug("Processing image, size:" + pImage.getSize() + "image original name:" + pImage.getOriginalFilename());

        String fileName = System.currentTimeMillis() + ImageUtils.getSuffixFromFileName(pImage.getOriginalFilename());
        ImageUtils.writeFileToDisk(pImage, pUserId, fileName);
        Image image;
        String relativePath;
        relativePath = ImageUtils.getRelativePath(pUserId);
        image = new Image();
        image.setImgName(fileName);
        image.setImgPath(StringUtils.normalizeUrlNoEndSeparator(relativePath));
        image.setNewsId(pNewsId);
        getNewsManager().insertImage(image);
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
