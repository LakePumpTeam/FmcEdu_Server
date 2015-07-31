package com.fmc.edu.model.push;

/**
 * Created by Yu on 2015/7/30.
 */
public enum PushMessageType {
    TYPE_TEACHER_NOTIFY_PARENT_NORTH_DELTA(0),
    TYPE_PARENT_NORTH_DELTA(1),
    TYPE_PARENT_SEND_CHILD(2),
    TYPE_WARNING_LOST_CARD(3),
    TYPE_CLOCK_IN_CHILD_IN(4),
    TYPE_CLOCK_IN_CHILD_OUT(5);

    private Integer mValue;

    PushMessageType(Integer pValue) {
        mValue = pValue;
    }

    public Integer getValue() {
        return mValue;
    }
}
