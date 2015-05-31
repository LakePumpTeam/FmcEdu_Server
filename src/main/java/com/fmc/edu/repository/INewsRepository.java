package com.fmc.edu.repository;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.Image;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.util.pagenation.Pagination;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
public interface INewsRepository {

    String QUERY_NEWS_LIST_BY_TYPE = "com.fmc.edu.news.queryNewsListByNewType";

    List<News> queryNewsListByNewType(Pagination pPagination, int pNewsType);

    String QUERY_NEWS_MAX_ID_BY_NEWS_TYPE = "com.fmc.edu.news.queryNewsMaxIdByNewsType";

    int queryNewsMaxIdByNewsType(int pNewsType);

    String QUERY_NEWS_SLIDE_LIST = "com.fmc.edu.news.querySlideListByNewType";

    List<Slide> querySlideList(Timestamp pStartDate, Timestamp pEndDate);

    String QUERY_NEWS_DETAIL = "com.fmc.edu.news.queryNewsDetail";

    News queryNewsDetail(int pNewsId);

    String INSERT_COMMENTS = "com.fmc.edu.news.insertComment";

    boolean insertComment(Comments pComments);

    String DELETE_COMMENTS = "com.fmc.edu.news.deleteComment";

    boolean deleteComment(int pCommentId);

    String INSERT_NEWS = "com.fmc.edu.news.insertNews";

    boolean insertNews(News pNews);

    String QUERY_LAST_INSERT_NEWS_ID_BY_AUTHOR = "com.fmc.edu.news.queryLastInsertNewsTypeNewsIdByAuthor";

    int queryLastInsertNewsTypeNewsIdByAuthor(int pUserId, int pNewsType);

    String INSERT_IMAGE = "com.fmc.edu.news.insertImage";

    boolean insertImage(Image pImage);

    String QUERY_COMMENTS_BY_NEWSID_AND_PROFILEID = "com.fmc.edu.news.queryCommentsByNewsIdAndProfileId";

    List<Comments> queryCommentsByNewsIdAndProfileId(int pUserId, int pNewsId);

    String IS_LIKED_NEWS = "com.fmc.edu.news.isLikedNews";

    boolean isLikedNews(int pUserId, int pNewsId);

    String QUERY_ALL_NEWS_MAX_ID = "com.fmc.edu.news.getAllNewsMaxNewsId";

    List<Map<Integer, Integer>> getAllNewsMaxNewsId();

    String UPDATE_NEWS = "com.fmc.edu.news.updateNews";

    boolean updateNews(News pNews);

    String UPDATE_NEWS_CACHE_BATCH = "com.fmc.edu.news.updateNewsCacheBatch";

    boolean updateNewsCacheBatch(List<Cache> pCacheList);
}
