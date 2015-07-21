package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
public class News extends BaseBean {

	private int mAuthor;
	private String mAuthorName;
	private String mSubject;
	private String mContent;
	private Integer mNewsType;
	private int mRefId;
	private boolean mAvailable;
	private boolean mApproved;
	private int mApprovedBy;
	private String mApprovedByName;
	private Timestamp mApprovedDate;
	private int mLike;
	private boolean mPopular;
	private Timestamp mPublishDate;
	private Timestamp mCreationDate;

	private List<Image> mImageUrls;

	public News() {

	}

	public News(final int pRefId, final String pContent, final int pAuthor, final Integer pNewsType) {
		mRefId = pRefId;
		mContent = pContent;
		mNewsType = pNewsType;
		mAuthor = pAuthor;
	}

	public int getAuthor() {
		return mAuthor;
	}

	public void setAuthor(int pAuthor) {
		mAuthor = pAuthor;
	}

	public String getSubject() {
		return mSubject;
	}

	public void setSubject(String pSubject) {
		mSubject = pSubject;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String pContent) {
		mContent = pContent;
	}

	public Integer getNewsType() {
		return mNewsType;
	}

	public void setNewsType(Integer pNewsType) {
		mNewsType = pNewsType;
	}

	public int getRefId() {
		return mRefId;
	}

	public void setRefId(int pRefId) {
		mRefId = pRefId;
	}

	public boolean isApproved() {
		return mApproved;
	}

	public void setApproved(boolean pApproved) {
		mApproved = pApproved;
	}

	public int getApprovedBy() {
		return mApprovedBy;
	}

	public void setApprovedBy(int pApprovedBy) {
		mApprovedBy = pApprovedBy;
	}

	public Timestamp getApprovedDate() {
		return mApprovedDate;
	}

	public void setApprovedDate(Timestamp pApprovedDate) {
		mApprovedDate = pApprovedDate;
	}

	public int getLike() {
		return mLike;
	}

	public void setLike(int pLike) {
		mLike = pLike;
	}

	public Timestamp getPublishDate() {
		return mPublishDate;
	}

	public void setPublishDate(Timestamp pPublishDate) {
		mPublishDate = pPublishDate;
	}

	public List<Image> getImageUrls() {
		return mImageUrls;
	}

	public void setImageUrls(List<Image> pImageUrls) {
		mImageUrls = pImageUrls;
	}

	public Timestamp getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(Timestamp pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public boolean isPopular() {
		return mPopular;
	}

	public void setPopular(final boolean pPopular) {
		mPopular = pPopular;
	}

	public String getAuthorName() {
		return mAuthorName;
	}

	public void setAuthorName(final String pAuthorName) {
		mAuthorName = pAuthorName;
	}

	public String getApprovedByName() {
		return mApprovedByName;
	}

	public void setApprovedByName(final String pApprovedByName) {
		mApprovedByName = pApprovedByName;
	}
}
