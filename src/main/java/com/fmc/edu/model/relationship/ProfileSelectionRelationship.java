package com.fmc.edu.model.relationship;

import java.sql.Timestamp;

/**
 * Created by Yu on 6/2/2015.
 */
public class ProfileSelectionRelationship extends BaseRelationship {
    private Integer mNewsId;
    private Integer mSelectionId;
    private Integer mProfileId;
    private Timestamp mCreationDate;

    public Integer getNewsId() {
        return mNewsId;
    }

    public void setNewsId(Integer pNewsId) {
        mNewsId = pNewsId;
    }

    public Integer getSelectionId() {
        return mSelectionId;
    }

    public void setSelectionId(Integer pSelectionId) {
        mSelectionId = pSelectionId;
    }

    public Integer getProfileId() {
        return mProfileId;
    }

    public void setProfileId(Integer pProfileId) {
        mProfileId = pProfileId;
    }
}
