package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 2015/5/21.
 */
public class Comments extends BaseBean {

    private int mRefId;

    private int mProfileId;

    private String mComment;

    private Timestamp mCreationDate;

    private Boolean mAvailable;

    public int getRefId() {
        return mRefId;
    }

    public void setRefId(int pRefId) {
        mRefId = pRefId;
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

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean pAvailable) {
        mAvailable = pAvailable;
    }
}
