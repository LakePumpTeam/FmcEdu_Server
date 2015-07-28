package com.fmc.edu.model.clockin;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 7/18/2015.
 */
public class MagneticCard extends BaseBean {
    private String mCardNo;
    private String mComments;
    private Timestamp mCreationDate;
    private Integer mCardType;

    public String getCardNo() {
        return mCardNo;
    }

    public void setCardNo(String pCardNo) {
        mCardNo = pCardNo;
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String pComments) {
        mComments = pComments;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }

    /**
     * 0:Parent 1:Student
     */
    public Integer getCardType() {
        return mCardType;
    }

    public void setCardType(Integer pCardType) {
        mCardType = pCardType;
    }
}
