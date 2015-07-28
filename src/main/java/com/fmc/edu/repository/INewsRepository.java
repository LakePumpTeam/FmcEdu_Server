package com.fmc.edu.repository;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
import com.fmc.edu.util.pagenation.Pagination;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
public interface INewsRepository {

	String QUERY_NEWS_LIST_BY_CLASS_ID = "com.fmc.edu.news.queryNewsListByClassId";

	List<News> queryNewsListByClassId(int pNewsType, final int pClassId, Pagination pPagination);

	String QUERY_NEWS_MAX_ID_BY_NEWS_TYPE = "com.fmc.edu.news.queryNewsMaxIdByNewsType";

	int queryNewsMaxIdByNewsType(int pNewsType);

	String QUERY_NEWS_SLIDE_LIST = "com.fmc.edu.news.querySlideList";

	List<Slide> querySlideList(final boolean pAvailable, Timestamp pStartDate, Timestamp pEndDate);

	String QUERY_NEWS_DETAIL = "com.fmc.edu.news.queryNewsDetail";

	News queryNewsDetail(int pNewsId);

	String INSERT_COMMENTS = "com.fmc.edu.news.insertComment";

	boolean insertComment(Comments pComments);

	String DELETE_COMMENTS = "com.fmc.edu.news.deleteComment";

	boolean deleteComment(int pCommentId);

	String INSERT_NEWS = "com.fmc.edu.news.insertNews";

	boolean insertNews(News pNews);

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

	String QUERY_PROFILE_SELECTION_RELATIONSHIP_COUNT = "com.fmc.edu.news.queryProfileSelectionRelationshipCount";

	int queryProfileSelectionRelationshipCount(int pNewsId);

	String QUERY_PROFILE_SELECTION_RELATIONSHIP = "com.fmc.edu.news.queryProfileSelectionRelationship";

	List<ProfileSelectionRelationship> queryProfileSelectionRelationships(int pNewsId, int pUserId);

	String QUERY_SELECTION_BY_NEWS_ID = "com.fmc.edu.news.querySelectionByNewsId";

	List<Selection> querySelectionByNewsId(int pNewsId);

	String QUERY_SELECTION_BY_ID = "com.fmc.edu.news.querySelectionById";

	Selection querySelectionById(int pSelectionId);

	String INSERT_PROFILE_SELECTION_MAP = "com.fmc.edu.news.insertProfileSelectionMap";

	int insertProfileSelectionMap(ProfileSelectionRelationship pProfileSelectionRelationship);

	String INSERT_SELECTION = "com.fmc.edu.news.insertSelection";

	int insertSelection(List<Selection> pSelections);

	String UPDATE_SLIDES = "com.fmc.edu.news.updateSlidesBatch";

	boolean UpdateSlidesBatch(List<Slide> pSlides);

	String CREATE_SLIDE = "com.fmc.edu.news.createSlide";

	boolean createSlide(Slide pSlide);

	String UPDATE_NEWS_AVAILABLE = "com.fmc.edu.news.updateNewsAvailable";

	boolean updateNewsAvailable(int pNewsId, final boolean pAvailable);

	String CREATE_OPTIONS = "com.fmc.edu.news.createOptions";

	boolean createOptions(List<Selection> pOptions);
}
