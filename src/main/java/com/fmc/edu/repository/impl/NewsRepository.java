package com.fmc.edu.repository.impl;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
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
    public News queryNewsDetail(int pNewsId) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("newsId", pNewsId);
        return getSqlSession().selectOne(QUERY_NEWS_DETAIL, params);
    }

    @Override
    public boolean insertComment(Comments pComments) {
        return getSqlSession().insert(INSERT_COMMENTS, pComments) > 0;
    }

    @Override
    public boolean deleteComment(int pCommentId) {
        return getSqlSession().update(DELETE_COMMENTS, pCommentId) > 0;
    }

    @Override
    public boolean insertNews(News pNews) {
        return getSqlSession().insert(INSERT_NEWS, pNews) > 0;
    }

    @Override
    public boolean insertImage(Image pImage) {
        return getSqlSession().insert(INSERT_IMAGE, pImage) > 0;
    }

    @Override
    public List<Comments> queryCommentsByNewsIdAndProfileId(int pUserId, int pNewsId) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("userId", pUserId);
        params.put("newsId", pNewsId);
        return getSqlSession().selectList(QUERY_COMMENTS_BY_NEWSID_AND_PROFILEID, params);
    }

    @Override
    public boolean isLikedNews(int pUserId, int pNewsId) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("userId", pUserId);
        params.put("newsId", pNewsId);
        Integer count = getSqlSession().selectOne(IS_LIKED_NEWS, params);
        return !(count == null || count == 0);
    }

    @Override
    public List<Map<Integer, Integer>> getAllNewsMaxNewsId() {
        return getSqlSession().selectList(QUERY_ALL_NEWS_MAX_ID);
    }

    @Override
    public boolean updateNews(News pNews) {
        return getSqlSession().update(UPDATE_NEWS, pNews) > 0;
    }

    @Override
    public boolean updateNewsCacheBatch(final List<Cache> pCacheList) {
        return getSqlSession().update(UPDATE_NEWS_CACHE_BATCH, pCacheList) > 0;
    }

    @Override
    public int queryProfileSelectionRelationshipCount(int pNewsId) {
        return getSqlSession().selectOne(QUERY_PROFILE_SELECTION_RELATIONSHIP_COUNT, pNewsId);
    }

    @Override
    public ProfileSelectionRelationship queryProfileSelectionRelationship(int pNewsId, int pUserId) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("newsId", pNewsId);
        params.put("userId", pUserId);
        return getSqlSession().selectOne(QUERY_PROFILE_SELECTION_RELATIONSHIP, params);
    }

    @Override
    public List<Selection> querySelectionByNewsId(int pNewsId) {
        return getSqlSession().selectList(QUERY_SELECTION_BY_NEWS_ID, pNewsId);
    }

    @Override
    public Selection querySelectionById(int pSelectionId) {
        return getSqlSession().selectOne(QUERY_SELECTION_BY_ID, pSelectionId);
    }

    @Override
    public int insertProfileSelectionMap(ProfileSelectionRelationship pProfileSelectionRelationship) {
        return getSqlSession().insert(INSERT_PROFILE_SELECTION_MAP, pProfileSelectionRelationship);
    }

    @Override
    public int insertSelection(List<Selection> pSelections) {
        return getSqlSession().insert(INSERT_SELECTION, pSelections);
    }
}
