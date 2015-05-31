package com.fmc.edu.model.relationship;

/**
 * Created by Yu on 5/31/2015.
 */
public class TaskStudentsRelationship extends BaseRelationship {
    private Integer mTaskId;
    private Integer mStudentId;
    private Boolean mCompleted;

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

    public Boolean getCompleted() {
        return mCompleted;
    }

    public void setCompleted(Boolean pCompleted) {
        mCompleted = pCompleted;
    }
}
