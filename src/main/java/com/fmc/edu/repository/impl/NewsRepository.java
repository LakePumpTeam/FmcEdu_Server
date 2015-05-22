package com.fmc.edu.repository.impl;

import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.INewsRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
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
        params.put("newsType", pNewsType);
        return getSqlSession().selectList(QUERY_NEWS_LIST_BY_TYPE, params);
    }

    @Override
    public int queryNewsMaxIdByNewsType(int pNewsType) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("newsType", pNewsType);
        return getSqlSession().selectOne(QUERY_NEWS_MAX_ID_BY_NEWS_TYPE, params);
    }

    @Override
    public List<Slide> querySlideList(Timestamp pStartDate, Timestamp pEndDate) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("startDate", pStartDate);
        params.put("endDate", pEndDate);
        return getSqlSession().selectList(QUERY_NEWS_SLIDE_LIST, params);
    }

    @Override
    public List<Slide> queryNewsDetail(int pUserId, int pNewsId) {
        return null;
    }

    @Override
    public boolean insertComment(Comments pComments) {
        return getSqlSession().insert(INSERT_COMMENTS, pComments) > 0;
    }

}
