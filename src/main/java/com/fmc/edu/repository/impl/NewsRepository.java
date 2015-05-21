package com.fmc.edu.repository.impl;

import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.INewsRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Repository(value = "newsRepository")
public class NewsRepository extends BaseRepository implements INewsRepository {
    @Override
    public List<News> queryNewsListByNewType(Pagination pPagination, int pNewsType) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("newType", pNewsType);
        return getSqlSession().selectList(QUERY_NEWS_LIST_BY_TYPE, params);
    }

    @Override
    public boolean markeNewAsRead(int pUserId, NewsType pNewsType) {
        return false;
    }
}
