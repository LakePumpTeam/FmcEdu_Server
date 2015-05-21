package com.fmc.edu.model.news;

import com.fmc.edu.model.BaseBean;

/**
 * Created by Yu on 2015/5/21.
 */
public class Image extends BaseBean {
    private int mNewsId;
    private String mImgName;
    private String mImgPath;

    public int getNewsId() {
        return mNewsId;
    }

    public void setNewsId(int pNewsId) {
        mNewsId = pNewsId;
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
}
