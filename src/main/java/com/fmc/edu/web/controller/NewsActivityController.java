package com.fmc.edu.web.controller;

import com.fmc.edu.cache.CacheContent;
import com.fmc.edu.cache.CacheManager;
import com.fmc.edu.cache.impl.newslike.NewsLikeCacheContent;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.exception.NewsException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
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
        ResponseBean responseBean = new ResponseBean(pRequest);

        TransactionStatus txStatus = ensureTransaction();
        try {
            Pagination pagination = buildPagination(pRequest);
            String userIdStr = pRequest.getParameter("userId");
            String classIdStr = pRequest.getParameter("classId");
            String typeStr = pRequest.getParameter("type");
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_EMPTY, userIdStr);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(classIdStr)) {
                throw new ProfileException(ResourceManager.VALIDATION_SCHOOL_CLASS_ID_ERROR, classIdStr);
            }
            if (!StringUtils.isNumeric(typeStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_PROVINCE_TYPE_ERROR, typeStr);
                return output(responseBean);
            }

            BaseProfile currentUser = getMyAccountManager().findUserById(userIdStr);
            if (currentUser == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userIdStr);
                return output(responseBean);
            }

            int newsType = Integer.valueOf(typeStr);
            int classId = Integer.valueOf(classIdStr);
            List<News> newsList = getNewsManager().queryNewsListByClassId(newsType, classId, pagination);
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
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            //TODO need to confirm the date period
            List<Slide> slideList = getNewsManager().querySlideList(true, DateUtils.getDaysLater(-1), DateUtils.getDaysLater(30));
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
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            String newsIdStr = pRequest.getParameter("newsId");
            String userIdStr = pRequest.getParameter("userId");
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_ID_ERROR, newsIdStr);
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

            getResponseBuilder().buildNewsDetailResponse(responseBean, news, userId);
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping("/postComment")
    @ResponseBody
    public String postComment(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            String newsIdStr = pRequest.getParameter("newsId");
            String userIdStr = pRequest.getParameter("userId");
            String contentStr = pRequest.getParameter("content");
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userIdStr);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_ID_ERROR, newsIdStr);
                return output(responseBean);
            }
            if (StringUtils.isBlank(contentStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_CONTENT_EMPTY);
                return output(responseBean);
            }
            Comments comments = new Comments();
            comments.setRefId(Integer.valueOf(newsIdStr));
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
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            String userIdStr = pRequest.getParameter("userId");
            String newsIdStr = pRequest.getParameter("newsId");
            String isLikeStr = pRequest.getParameter("isLike");

            if (!RepositoryUtils.idIsValid(userIdStr)) {
                throw new NewsException(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userIdStr);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                throw new NewsException(ResourceManager.VALIDATION_NEWS_ID_ERROR, newsIdStr);
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
                throw new NewsException(ResourceManager.ERROR_COMMON_OPERATION_FAILED);
            }

            // handle cache
            NewsLikeCacheContent cacheContent = (NewsLikeCacheContent) getCacheManager().getCacheContent(CacheManager.CACHE_CONTENT_NEWS_LIKE);
            Map<String, Object> params = new HashMap<>();
            params.put(CacheContent.UPDATE_TYPE, isLike ? NewsLikeCacheContent.PLUS_ONE : NewsLikeCacheContent.MINUS_ONE);
            cacheContent.updateCache(String.valueOf(newsId), params);

            //TODO integration with memory cache for like numbers
            return output(responseBean);
        } catch (NewsException ex) {
            responseBean.addBusinessMsg(ex.getMessage(), ex.getArgs());
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
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            String userIdStr = pRequest.getParameter("userId");
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userIdStr);
                return output(responseBean);
            }
            responseBean.addData(getNewsManager().getReadNewsStatus(Integer.valueOf(userIdStr)));
            return output(responseBean);
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/postClassNews", method = RequestMethod.POST)
    @ResponseBody
    public String postClassNews(final HttpServletRequest pRequest,
                                final HttpServletResponse pResponse, String userId, @RequestParam(value = "subject", required = false) String subject,
                                String content, String classId, @RequestParam(value = "imgs", required = false) MultipartFile[] imgs) {
        ResponseBean responseBean = new ResponseBean(pRequest);

        TransactionStatus txStatus = ensureTransaction();
        try {
            if (!RepositoryUtils.idIsValid(userId)) {
                throw new ProfileException(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userId);
            }
            if (!RepositoryUtils.idIsValid(classId)) {
                throw new ProfileException(ResourceManager.VALIDATION_SCHOOL_CLASS_ID_ERROR, classId);
            }
            if (StringUtils.isBlank(content)) {
                throw new NewsException(ResourceManager.VALIDATION_NEWS_CONTENT_EMPTY);
            }
            // convert ids to integer type
            int userIdInt = Integer.valueOf(userId);
            int classIdInt = Integer.valueOf(classId);
            // create bean to persist
            News classNews = new News(classIdInt, content, userIdInt, NewsType.CLASS_DYNAMICS);
            if (StringUtils.isBlank(subject)) {
                subject = StringUtils.EMPTY;
            }
            classNews.setSubject(subject);
            // always set approved for now
            classNews.setApproved(true);
            // persist news & images
            if (!getNewsManager().insertNews(classNews)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_POST_CLASS_NEWS_FAILED);
                return output(responseBean);
            }

            getNewsManager().saveNewsImage(imgs, userId, classNews.getId());

            return output(responseBean);
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            LOG.error(e.fillInStackTrace());
            txStatus.setRollbackOnly();
        } catch (NewsException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
            LOG.error(e.fillInStackTrace());
            txStatus.setRollbackOnly();
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e.fillInStackTrace());
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping("/submitParticipation")
    @ResponseBody
    public String submitParticipation(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            String userIdStr = pRequest.getParameter("userId");
            String newsIdStr = pRequest.getParameter("newsId");
            String selectionIdStr = pRequest.getParameter("selectionIds");

            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userIdStr);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_ID_ERROR, newsIdStr);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(selectionIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_SELECTION_EMPTY);
                return output(responseBean);
            }

            String[] selectionIds = selectionIdStr.split(",");
            int decodeUserId;
            int decodeNewsId;
            int decodeSelectionId;
            if (selectionIds != null && selectionIds.length > 0) {
                for (String selectionId : selectionIds) {
                    LOG.debug("Submit selection id:" + selectionId);
                    decodeUserId = Integer.valueOf(userIdStr);
                    decodeNewsId = Integer.valueOf(newsIdStr);
                    decodeSelectionId = Integer.valueOf(selectionId);

                    ProfileSelectionRelationship profileSelectionRelationship = getNewsManager().queryProfileSelectionRelationship(decodeNewsId, decodeUserId);
                    if (profileSelectionRelationship != null) {
                        Selection selection = getNewsManager().querySelectionById(profileSelectionRelationship.getSelectionId());
                        responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_DUPLICATION_PARTICIPATION_ERROR, selection.getSelection());
                        return output(responseBean);
                    }
                    ProfileSelectionRelationship tempProfileSelectionRelationship = new ProfileSelectionRelationship();
                    tempProfileSelectionRelationship.setNewsId(decodeNewsId);
                    tempProfileSelectionRelationship.setProfileId(decodeUserId);
                    tempProfileSelectionRelationship.setSelectionId(decodeSelectionId);
                    if (getNewsManager().insertProfileSelectionMap(tempProfileSelectionRelationship) == 0) {
                        responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_PARTICIPATION_FAILED);
                        return output(responseBean);
                    }
                }
            }
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            txStatus.setRollbackOnly();
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping("/requestDisableNews")
    @ResponseBody
    public String disableNews(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus ts = ensureTransaction();
        try {
            String userIdStr = pRequest.getParameter("userId");
            String newsIdStr = pRequest.getParameter("newsId");
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_ERROR, userIdStr);
                return output(responseBean);
            }
            if (!RepositoryUtils.idIsValid(newsIdStr)) {
                responseBean.addBusinessMsg(ResourceManager.VALIDATION_NEWS_ID_ERROR, newsIdStr);
                return output(responseBean);
            }
            int decodeNewsId = Integer.valueOf(newsIdStr);
            if (!getNewsManager().disableNewsById(decodeNewsId)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_DISABLE_NEWS_FAILED);
            }
        } catch (Exception e) {
            LOG.error(e);
            ts.setRollbackOnly();
        } finally {
            getTransactionManager().commit(ts);
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
