package com.fmc.edu.manager;

import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.Image;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.service.impl.NewsService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
@Service("newsManager")
public class NewsManager {
    @Resource(name = "newsService")
    private NewsService mNewsService;

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

    public int queryNewsMaxIdByNewsType(int pNewsType) {
        return getNewsService().queryNewsMaxIdByNewsType(pNewsType);
    }

    public boolean insertImage(Image pImage) {
        return getNewsService().insertImage(pImage);
    }

    public NewsService getNewsService() {
        return mNewsService;
    }

    public void setNewsService(NewsService pNewsService) {
        mNewsService = pNewsService;
    }
}
