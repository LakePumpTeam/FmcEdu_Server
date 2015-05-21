package com.fmc.edu.model.course;

import com.fmc.edu.model.BaseBean;

/**
 * Created by Yu on 2015/5/21.
 */
public class TimeTable extends BaseBean {
    private int mClassId;
    private boolean mAvaliable;

    public int getClassId() {
        return mClassId;
    }

    public void setClassId(int pClassId) {
        mClassId = pClassId;
    }

    public boolean isAvaliable() {
        return mAvaliable;
    }

    public void setAvaliable(boolean pAvaliable) {
        mAvaliable = pAvaliable;
    }
}
