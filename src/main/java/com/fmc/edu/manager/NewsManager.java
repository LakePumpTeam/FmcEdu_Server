package com.fmc.edu.manager;

import com.fmc.edu.model.news.News;
import com.fmc.edu.service.impl.NewsService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public NewsService getNewsService() {
        return mNewsService;
    }

    public void setNewsService(NewsService pNewsService) {
        mNewsService = pNewsService;
    }
}
