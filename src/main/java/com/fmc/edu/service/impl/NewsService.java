package com.fmc.edu.service.impl;

import com.fmc.edu.cache.Cache;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
import com.fmc.edu.repository.INewsRepository;
import com.fmc.edu.repository.impl.NewsRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Service("newsService")
public class NewsService {

	@Resource(name = "newsRepository")
	private INewsRepository mNewsRepository;

	public List<News> queryNewsListByClassId(int pNewsType, final int pClassId, Pagination pPagination) {
		return getNewsRepository().queryNewsListByClassId(pNewsType, pClassId, pPagination);
	}

	public int queryNewsMaxIdByNewsType(int pNewsType) {
		return getNewsRepository().queryNewsMaxIdByNewsType(pNewsType);
	}

	public List<Slide> querySlideList(final boolean pAvailable, Timestamp pStartDate, Timestamp pEndDate) {
		return getNewsRepository().querySlideList(pAvailable, pStartDate, pEndDate);
	}

	public INewsRepository getNewsRepository() {
		return mNewsRepository;
	}

	public boolean insertComment(Comments pComments) {

		return getNewsRepository().insertComment(pComments);
	}

	public boolean deleteComment(int pCommentId) {
		return getNewsRepository().deleteComment(pCommentId);
	}

	public News queryNewsDetail(int pNewsId) {
		return getNewsRepository().queryNewsDetail(pNewsId);
	}

	public boolean insertNews(News pNews) {
		return getNewsRepository().insertNews(pNews);
	}

	public List<Comments> queryCommentsByNewsIdAndProfileId(int pUserId, int pNewsId) {
		return getNewsRepository().queryCommentsByNewsIdAndProfileId(pUserId, pNewsId);
	}

	public boolean updateNewsCacheBatch(final List<Cache> pCacheList) {
		return getNewsRepository().updateNewsCacheBatch(pCacheList);
	}

	public boolean isLikedNews(int pUserId, int pNewsId) {
		return getNewsRepository().isLikedNews(pUserId, pNewsId);
	}

	public List<Map<Integer, Integer>> getAllNewsMaxNewsId() {
		return getNewsRepository().getAllNewsMaxNewsId();
	}

	public boolean updateNews(News pNews) {
		return getNewsRepository().updateNews(pNews);
	}

	public boolean insertImage(Image pImage) {
		return getNewsRepository().insertImage(pImage);
	}

	public int queryProfileSelectionRelationshipCount(int pNewsId) {
		return getNewsRepository().queryProfileSelectionRelationshipCount(pNewsId);
	}

	public List<ProfileSelectionRelationship> queryProfileSelectionRelationships(int pNewsId, int pUserId) {
		return getNewsRepository().queryProfileSelectionRelationships(pNewsId, pUserId);
	}

	public List<Selection> querySelectionByNewsId(int pNewsId) {
		return getNewsRepository().querySelectionByNewsId(pNewsId);
	}

	public Selection querySelectionById(int pSelectionId) {
		return getNewsRepository().querySelectionById(pSelectionId);
	}

	public int insertProfileSelectionMap(ProfileSelectionRelationship pProfileSelectionRelationship) {
		return getNewsRepository().insertProfileSelectionMap(pProfileSelectionRelationship);
	}

	public int insertSelection(List<Selection> pSelections) {
		return getNewsRepository().insertSelection(pSelections);
	}

	public boolean UpdateSlidesBatch(final List<Slide> pSlides) {
		return getNewsRepository().UpdateSlidesBatch(pSlides);
	}

	public void setNewsRepository(NewsRepository pNewsRepository) {
		mNewsRepository = pNewsRepository;
	}

	public boolean createSlide(final Slide pSlide) {
		return getNewsRepository().createSlide(pSlide);
	}

	public boolean updateNewsAvailable(final int pNewsId, final boolean pAvailable) {
		return getNewsRepository().updateNewsAvailable(pNewsId, pAvailable);
	}

	public boolean createOptions(final List<Selection> pOptions) {
		return getNewsRepository().createOptions(pOptions);
	}
}
