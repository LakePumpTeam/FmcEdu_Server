package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 6/2/2015.
 */
public class Selection extends BaseBean {
    private Integer mNewsId;
    private String mSelection;
    private int mSortOrder;
    private Timestamp mCreationDate;

    public Integer getNewsId() {
        return mNewsId;
    }

    public void setNewsId(Integer pNewsId) {
        mNewsId = pNewsId;
    }

    public String getSelection() {
        return mSelection;
    }

    public void setSelection(String pSelection) {
        mSelection = pSelection;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(int pSortOrder) {
        mSortOrder = pSortOrder;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }
}
