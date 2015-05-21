package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

/**
 * Created by Yu on 2015/5/21.
 */
public class UserMessage extends BaseBean {
    private int mMessageType;
    private String mContent;
    private int mRefId;
    private int mReading;

    public int getMessageType() {
        return mMessageType;
    }

    public void setMessageType(int pMessageType) {
        mMessageType = pMessageType;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String pContent) {
        mContent = pContent;
    }

    public int getRefId() {
        return mRefId;
    }

    public void setRefId(int pRefId) {
        mRefId = pRefId;
    }

    public int getReading() {
        return mReading;
    }

    public void setReading(int pReading) {
        mReading = pReading;
    }
}
