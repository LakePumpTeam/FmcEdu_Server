package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
public class News extends BaseBean {
    private int mAuthor;
    private String mSubject;
    private String mContent;
    private Integer mNewsType;
    private int mRefId;
    private Boolean mApproved;
    private int mApprovedBy;
    private Timestamp mApprovedDate;
    private int mLike;
    private Timestamp mPublishDate;
    private Timestamp mCreationDate;

    private List<Image> mImageUrls;

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

    public Boolean isApproved() {
        return mApproved;
    }

    public void setApproved(Boolean pApproved) {
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
}
