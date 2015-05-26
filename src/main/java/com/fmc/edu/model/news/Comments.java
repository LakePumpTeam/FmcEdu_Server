package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 2015/5/21.
 */
public class Comments extends BaseBean {

    private int mNewsId;

    private int mProfileId;

    private String mComment;

    private Timestamp mCreationDate;

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

    public String getComment() {
        return mComment;
    }

    public void setComment(String pComment) {
        mComment = pComment;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(final Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }
}
