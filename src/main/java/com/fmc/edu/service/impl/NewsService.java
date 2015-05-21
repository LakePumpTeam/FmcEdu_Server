package com.fmc.edu.service.impl;

import com.fmc.edu.model.news.News;
import com.fmc.edu.repository.impl.NewsRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public NewsRepository getNewsRepository() {
        return mNewsRepository;
    }

    public void setNewsRepository(NewsRepository pNewsRepository) {
        mNewsRepository = pNewsRepository;
    }
}
