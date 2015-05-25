package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.service.impl.NewsService;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Service("newsManager")
public class NewsManager {
    @Resource(name = "newsService")
    private NewsService mNewsService;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    public List<News> queryNewsListByNewType(Pagination pPagination, int pNewsType) {

        return getNewsService().queryNewsListByNewType(pPagination, pNewsType);
    }

    public List<Slide> querySlideList(Timestamp pStartDate, Timestamp pEndDate) {
        return getNewsService().querySlideList(pStartDate, pEndDate);
    }

    public boolean insertComment(Comments pComments) {

        return getNewsService().insertComment(pComments);
    }

    public boolean insertNews(News pNews) {
        return getNewsService().insertNews(pNews);
    }

    public int queryLastInsertNewsTypeNewsIdByAuthor(int pUserId, int pNewsType) {
        return getNewsService().queryLastInsertNewsTypeNewsIdByAuthor(pUserId, pNewsType);
    }

    public News queryNewsDetail(int pNewsId) {
        return getNewsService().queryNewsDetail(pNewsId);
    }

    public List<Comments> queryCommentsByNewsIdAndProfileId(int pUserId, int pNewsId) {
        return getNewsService().queryCommentsByNewsIdAndProfileId(pUserId, pNewsId);
    }

    public int queryNewsMaxIdByNewsType(int pNewsType) {
        return getNewsService().queryNewsMaxIdByNewsType(pNewsType);
    }

    public boolean isLikedNews(int pUserId, int pNewsId) {
        return getNewsService().isLikedNews(pUserId, pNewsId);
    }

    public Map<String, Boolean> getReadNewsStatus(int pUserId) throws ProfileException {
        Map<String, Boolean> readNewsStatus = new HashMap<String, Boolean>(5);
        BaseProfile currentUser = getMyAccountManager().findUserById(String.valueOf(pUserId));
        if (currentUser == null) {
            throw new ProfileException(MyAccountManager.ERROR_NOT_FIND_USER);
        }
        List<Map<Integer, Integer>> maxNewsIdList = getAllNewsMaxNewsId();
        readNewsStatus.put("classNews", false);
        readNewsStatus.put("pcdNews", false);
        readNewsStatus.put("parentingClassNews", false);
        readNewsStatus.put("bbsNews", false);
        readNewsStatus.put("schoolNews", false);

        if (CollectionUtils.isEmpty(maxNewsIdList)) {
            return readNewsStatus;
        }
        for (Map<Integer, Integer> maxNewsId : maxNewsIdList) {
            Integer maxId = maxNewsId.get("maxId");
            if (maxId == null) {
                continue;
            }
            switch (maxNewsId.get("newsType")) {
                case NewsType.CLASS_DYNAMICS: {
                    readNewsStatus.put("classNews", maxId > currentUser.getLastCLId());
                    break;
                }
                case NewsType.PARENT_CHILD_EDU: {
                    readNewsStatus.put("pcdNews", maxId > currentUser.getLastPCEId());//maxNewsIdMap.get(NewsType.PARENT_CHILD_EDU) > currentUser.getLastCLId());
                    break;
                }
                case NewsType.PARENTING_CLASS: {
                    readNewsStatus.put("parentingClassNews", maxId > currentUser.getLastPCId());
                    break;
                }
                case NewsType.SCHOOL_BBS: {
                    readNewsStatus.put("bbsNews", maxId > currentUser.getLastBBSId());
                    break;
                }
                case NewsType.SCHOOL_DYNAMICS_ACTIVITY: {
                    if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
                        break;
                    }
                    readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDATId());
                    break;
                }
                case NewsType.SCHOOL_DYNAMICS_NEWS: {
                    if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
                        break;
                    }
                    readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDNWId());
                    break;
                }
                case NewsType.SCHOOL_DYNAMICS_NOTIFICATION: {
                    if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
                        break;
                    }
                    readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDNFId());
                    break;
                }

            }
        }
        return readNewsStatus;
    }

    public boolean insertImage(Image pImage) {
        return getNewsService().insertImage(pImage);
    }

    public List<Map<Integer, Integer>> getAllNewsMaxNewsId() {
        return getNewsService().getAllNewsMaxNewsId();
    }

    public boolean updateNews(News pNews) {
        return getNewsService().updateNews(pNews);
    }

    public NewsService getNewsService() {
        return mNewsService;
    }

    public void setNewsService(NewsService pNewsService) {
        mNewsService = pNewsService;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }
}
