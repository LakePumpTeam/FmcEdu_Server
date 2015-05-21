package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 2015/5/21.
 */
public class News extends BaseBean {
    private int mAuthor;
    private String mSubject;
    private String mContent;
    private int mNewType;
    private int mRefId;
    private boolean mApproved;
    private int mApprovedBy;
    private Timestamp mApprovedDate;
    private int mLike;
    private Timestamp mPublishDate;

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

    public int getNewType() {
        return mNewType;
    }

    public void setNewType(int pNewType) {
        mNewType = pNewType;
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
}
