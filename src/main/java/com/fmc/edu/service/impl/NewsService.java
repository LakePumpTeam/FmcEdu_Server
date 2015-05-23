package com.fmc.edu.service.impl;

import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.Image;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.repository.impl.NewsRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
@Service("newsService")
public class NewsService {
    @Resource(name = "newsRepository")
    private NewsRepository mNewsRepository;

    public List<News> queryNewsListByNewType(Pagination pPagination, int pNewsType) {
        return getNewsRepository().queryNewsListByNewType(pPagination, pNewsType);
    }

    public int queryNewsMaxIdByNewsType(int pNewsType) {
        return getNewsRepository().queryNewsMaxIdByNewsType(pNewsType);
    }

    public List<Slide> querySlideList(Timestamp pStartDate, Timestamp pEndDate) {
        return getNewsRepository().querySlideList(pStartDate, pEndDate);
    }

    public NewsRepository getNewsRepository() {
        return mNewsRepository;
    }

    public boolean insertComment(Comments pComments) {

        return getNewsRepository().insertComment(pComments);
    }

    public boolean insertNews(News pNews) {
        return getNewsRepository().insertNews(pNews);
    }

    public int queryLastInsertNewsTypeNewsIdByAuthor(int pUserId, int pNewsType) {
        return getNewsRepository().queryLastInsertNewsTypeNewsIdByAuthor(pUserId, pNewsType);
    }

    public boolean insertImage(Image pImage) {
        return getNewsRepository().insertImage(pImage);
    }

    public void setNewsRepository(NewsRepository pNewsRepository) {
        mNewsRepository = pNewsRepository;
    }
}
