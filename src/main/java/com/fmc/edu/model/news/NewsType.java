package com.fmc.edu.model.news;

/**
 * Created by Yu on 2015/5/21.
 */
public enum NewsType {
    PARENTING_CLASS(1),
    SCHOOL_DYNAMICS_ACTIVITY(2),
    SCHOOL_DYNAMICS_NOTIFY(3),
    SCHOOL_DYNAMICS_NEWS(4),
    CLASS_DYNAMICS(5);

    private int mValue;

    NewsType(int pValue) {
        this.mValue = pValue;
    }

    public String getName() {
        return this.name();
    }


    public int getValue() {
        return this.mValue;
    }
}
