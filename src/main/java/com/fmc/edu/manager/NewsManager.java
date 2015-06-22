package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
import com.fmc.edu.service.impl.NewsService;
import com.fmc.edu.util.ImageUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/21.
 */
@Service("newsManager")
public class NewsManager {

	private static final Logger LOG = Logger.getLogger(NewsManager.class);
	public static Object WRITE_FILE_LOCK = "writeFileLock";

	@Resource(name = "newsService")
	private NewsService mNewsService;

	@Resource(name = "myAccountManager")
	private MyAccountManager mMyAccountManager;

	public List<News> queryNewsListByClassId(int pNewsType, final int pClassId, Pagination pPagination) {
		return getNewsService().queryNewsListByClassId(pNewsType, pClassId, pPagination);
	}

	public List<Slide> querySlideList(final boolean pAvailable, Timestamp pStartDate, Timestamp pEndDate) {
		return getNewsService().querySlideList(pAvailable, pStartDate, pEndDate);
	}

	public boolean insertComment(Comments pComments) {
		return getNewsService().insertComment(pComments);
	}

	public boolean deleteComment(int pCommentId) {
		return getNewsService().deleteComment(pCommentId);
	}

	public boolean insertNews(News pNews) {
		return getNewsService().insertNews(pNews);
	}

	public News queryNewsDetail(int pNewsId) {
		return getNewsService().queryNewsDetail(pNewsId);
	}

	public List<Comments> queryCommentsByNewsIdAndProfileId(int pUserId, int pNewsId) {
		return getNewsService().queryCommentsByNewsIdAndProfileId(pUserId, pNewsId);
	}

	public int queryNewsMaxIdByNewsType(int pNewsType) {
		return getNewsService().queryNewsMaxIdByNewsType(pNewsType);
	}

	public boolean isLikedNews(int pUserId, int pNewsId) {
		return getNewsService().isLikedNews(pUserId, pNewsId);
	}

	public Map<String, Boolean> getReadNewsStatus(int pUserId) throws ProfileException {
		Map<String, Boolean> readNewsStatus = new HashMap<String, Boolean>(5);
		BaseProfile currentUser = getMyAccountManager().findUserById(String.valueOf(pUserId));
		if (currentUser == null) {
			throw new ProfileException(ResourceManager.ERROR_NOT_FIND_USER, String.valueOf(pUserId));
		}
		List<Map<Integer, Integer>> maxNewsIdList = getAllNewsMaxNewsId();
		readNewsStatus.put("classNews", false);
		readNewsStatus.put("pcdNews", false);
		readNewsStatus.put("parentingClassNews", false);
		readNewsStatus.put("bbsNews", false);
		readNewsStatus.put("schoolNews", false);

		if (CollectionUtils.isEmpty(maxNewsIdList)) {
			return readNewsStatus;
		}
		for (Map<Integer, Integer> maxNewsId : maxNewsIdList) {
			Integer maxId = maxNewsId.get("maxId");
			if (maxId == null) {
				continue;
			}
			switch (maxNewsId.get("newsType")) {
				case NewsType.CLASS_DYNAMICS: {
					readNewsStatus.put("classNews", maxId > currentUser.getLastCLId());
					break;
				}
				case NewsType.PARENT_CHILD_EDU: {
					readNewsStatus.put("pcdNews", maxId > currentUser.getLastPCEId());//maxNewsIdMap.get(NewsType.PARENT_CHILD_EDU) > currentUser.getLastCLId());
					break;
				}
				case NewsType.PARENTING_CLASS: {
					readNewsStatus.put("parentingClassNews", maxId > currentUser.getLastPCId());
					break;
				}
				case NewsType.SCHOOL_BBS: {
					readNewsStatus.put("bbsNews", maxId > currentUser.getLastBBSId());
					break;
				}
				case NewsType.SCHOOL_DYNAMICS_ACTIVITY: {
					if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
						break;
					}
					readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDATId());
					break;
				}
				case NewsType.SCHOOL_DYNAMICS_NEWS: {
					if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
						break;
					}
					readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDNWId());
					break;
				}
				case NewsType.SCHOOL_DYNAMICS_NOTIFICATION: {
					if (readNewsStatus.get("schoolNews") != null && readNewsStatus.get("schoolNews")) {
						break;
					}
					readNewsStatus.put("schoolNews", maxId > currentUser.getLastSDNFId());
					break;
				}

			}
		}
		return readNewsStatus;
	}

	public void saveNewsImage(MultipartFile[] pImages, String pUserId, int pNewsId) throws IOException {
		if (pImages == null || pImages.length == 0) {
			return;
		}
		for (MultipartFile img : pImages) {
			LOG.debug("Processing image, size:" + img.getSize() + " >>> image original name:" + img.getOriginalFilename());
			if (img.getSize() == 0) {
				return;
			}
			synchronized (NewsManager.WRITE_FILE_LOCK) {
				String fileName = System.currentTimeMillis() + ImageUtils.getSuffixFromFileName(img.getOriginalFilename());
				ImageUtils.writeNewsImageToDisk(img, pUserId, fileName);
				Image image;
				String relativePath;
				relativePath = ImageUtils.getRelativePath(pUserId);
				image = new Image();
				image.setImgName(fileName);
				image.setImgPath(StringUtils.normalizeUrlNoEndSeparator(relativePath));
				image.setNewsId(pNewsId);

				insertImage(image);
			}
		}

	}

	public boolean insertImage(Image pImage) {
		return getNewsService().insertImage(pImage);
	}

	public List<Map<Integer, Integer>> getAllNewsMaxNewsId() {
		return getNewsService().getAllNewsMaxNewsId();
	}

	public boolean updateNews(News pNews) {
		return getNewsService().updateNews(pNews);
	}

	public int queryProfileSelectionRelationshipCount(int pNewsId) {
		return getNewsService().queryProfileSelectionRelationshipCount(pNewsId);
	}

	public List<ProfileSelectionRelationship> queryProfileSelectionRelationships(int pNewsId, int pUserId) {
		return getNewsService().queryProfileSelectionRelationships(pNewsId, pUserId);
	}

	public List<Selection> querySelectionByNewsId(int pNewsId) {
		return getNewsService().querySelectionByNewsId(pNewsId);
	}

	public Selection querySelectionById(int pSelectionId) {
		return getNewsService().querySelectionById(pSelectionId);
	}

	public int insertProfileSelectionMap(ProfileSelectionRelationship pProfileSelectionRelationship) {
		return getNewsService().insertProfileSelectionMap(pProfileSelectionRelationship);
	}

	public int insertSelection(List<Selection> pSelections) {
		return getNewsService().insertSelection(pSelections);
	}

	public boolean createSlide(final Slide pSlide, final MultipartFile pImage) {
		LOG.debug("Processing image, size:" + pImage.getSize() + " >>> image original name:" + pImage.getOriginalFilename());
		boolean writeImageFile = false;
		if (pImage.getSize() == 0) {
			return writeImageFile;
		}
		String fileName = System.currentTimeMillis() + ImageUtils.getSuffixFromFileName(pImage.getOriginalFilename());
		synchronized (NewsManager.WRITE_FILE_LOCK) {
			writeImageFile = ImageUtils.writeSlideImageToDisk(pImage, fileName);
		}
		pSlide.setImgName(fileName);
		pSlide.setImgPath("");
		boolean created = getNewsService().createSlide(pSlide);
		if (created) {
			if (writeImageFile) {
				Image image;
				String relativePath = ImageUtils.getSlideImageBasePath();
				image = new Image();
				image.setImgName(fileName);
				//				image.setImgPath(StringUtils.normalizeUrlNoEndSeparator(relativePath));
				image.setNewsId(pSlide.getNewsId());
				return insertImage(image);
			}
		}
		return created;
	}

	public boolean UpdateSlidesBatch(final List<Slide> pSlides) {
		return getNewsService().UpdateSlidesBatch(pSlides);
	}

	public NewsService getNewsService() {
		return mNewsService;
	}

	public void setNewsService(NewsService pNewsService) {
		mNewsService = pNewsService;
	}

	public MyAccountManager getMyAccountManager() {
		return mMyAccountManager;
	}

	public void setMyAccountManager(MyAccountManager pMyAccountManager) {
		mMyAccountManager = pMyAccountManager;
	}

	public boolean disableNewsById(final int pNewsId) {
		return getNewsService().updateNewsAvailable(pNewsId, false);
	}
}
