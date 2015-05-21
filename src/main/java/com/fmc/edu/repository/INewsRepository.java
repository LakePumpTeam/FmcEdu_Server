package com.fmc.edu.repository;

import com.fmc.edu.model.news.News;
import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
public interface INewsRepository {
    String QUERY_NEWS_LIST_BY_TYPE = "com.fmc.edu.news.queryNewsListByNewType";

    List<News> queryNewsListByNewType(Pagination pPagination, int pNewsType);

    String QUERY_NEWS_MAX_ID_BY_NEWS_TYPE = "com.fmc.edu.news.queryNewsMaxIdByNewsType";

    int queryNewsMaxIdByNewsType(int pNewsType);
}
