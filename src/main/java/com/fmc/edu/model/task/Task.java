package com.fmc.edu.model.task;

import com.fmc.edu.model.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Yu on 5/31/2015.
 */
public class Task extends BaseBean {
    private String mTitle;
    private String mTask;
    private Timestamp mDeadline;
    private Integer mPublishUserId;
    private Boolean mAvailable;
    private Timestamp mCreationDate;

    public String getTask() {
        return mTask;
    }

    public void setTask(String pTask) {
        mTask = pTask;
    }

    public Timestamp getDeadline() {
        return mDeadline;
    }

    public void setDeadline(Timestamp pDeadline) {
        mDeadline = pDeadline;
    }

    public Integer getPublishUserId() {
        return mPublishUserId;
    }

    public void setPublishUserId(Integer pPublishUserId) {
        mPublishUserId = pPublishUserId;
    }

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean pAvailable) {
        mAvailable = pAvailable;
    }

    public Timestamp getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Timestamp pCreationDate) {
        mCreationDate = pCreationDate;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }
}
