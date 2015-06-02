package com.fmc.edu.model.relationship;

/**
 * Created by Yu on 5/31/2015.
 */
public class TaskStudentsRelationship extends BaseRelationship {
    private Integer mTaskId;
    private Integer mStudentId;
    private Boolean mCompleted;
    private Boolean mAvailable;

    public Integer getTaskId() {
        return mTaskId;
    }

    public void setTaskId(Integer pTaskId) {
        mTaskId = pTaskId;
    }

    public Integer getStudentId() {
        return mStudentId;
    }

    public void setStudentId(Integer pStudentId) {
        mStudentId = pStudentId;
    }

    public int getCompleted() {
        return mCompleted ? 1 : 0;
    }

    public void setCompleted(Boolean pCompleted) {
        mCompleted = pCompleted;
    }

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean pAvailable) {
        mAvailable = pAvailable;
    }
}
