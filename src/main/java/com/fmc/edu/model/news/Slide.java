package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 2015/5/21.
 */
public class Slide extends BaseBean {

    private String mSubject;
    private int mNewsId;
    private News mNews;
    private int mProfileId;
    private String mImgName;
    private String mImgPath;
    private int mOrder;
    private Timestamp mStartDate;
    private Timestamp mEndDate;
    private boolean mAvailable;
    private Timestamp mCreationDate;

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String pSubject) {
        mSubject = pSubject;
    }

    public int getNewsId() {
        return mNewsId;
    }

    public void setNewsId(int pNewsId) {
        mNewsId = pNewsId;
    }

    public int getProfileId() {
        return mProfileId;
    }

    public void setProfileId(int pProfileId) {
        mProfileId = pProfileId;
    }

    public String getImgName() {
        return mImgName;
    }

    public void setImgName(String pImgName) {
        mImgName = pImgName;
    }

    public String getImgPath() {
        return mImgPath;
    }

    public void setImgPath(String pImgPath) {
        mImgPath = pImgPath;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int pOrder) {
        mOrder = pOrder;
    }

    public Timestamp getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Timestamp pStartDate) {
        mStartDate = pStartDate;
    }

    public Timestamp getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Timestamp pEndDate) {
        mEndDate = pEndDate;
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean pAvailable) {
        mAvailable = pAvailable;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(final Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }

    public News getNews() {
        return mNews;
    }

    public void setNews(final News pNews) {
        mNews = pNews;
    }
}
