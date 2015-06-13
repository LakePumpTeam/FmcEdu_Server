package com.fmc.edu.service.impl;

import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.repository.ITaskRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/31/2015.
 */
@Service("taskService")
public class TaskService {
    @Resource(name = "taskRepository")
    private ITaskRepository mTaskRepository;

    public boolean publishTask(Task pTask) {
        return getTaskRepository().publishTask(pTask);
    }

    public boolean insertTaskStudentRelationship(List<TaskStudentsRelationship> pTaskStudentsRelationships) {
        return getTaskRepository().insertTaskStudentRelationship(pTaskStudentsRelationships);
    }

    public boolean editTask(Task pTask) {
        return getTaskRepository().editTask(pTask);
    }

    public boolean submitTask(int pTaskId, int pStudentId) {
        return getTaskRepository().submitTask(pTaskId, pStudentId);
    }

    public Map<String, Object> queryTaskListByParentId(int pParentId, String pFilter, Integer pStatus, Pagination pPagination) {
        return getTaskRepository().queryTaskListByParentId(pParentId, pFilter, pStatus, pPagination);
    }

    public Map<String, Object> queryTaskListByTeacherId(int pTeacherId, String pFilter, Integer pStatus, Pagination pPagination) {
        return getTaskRepository().queryTaskListByTeacherId(pTeacherId, pFilter, pStatus, pPagination);
    }

    public Task queryTaskDetail(int pTaskId) {
        return getTaskRepository().queryTaskDetail(pTaskId);
    }

    public boolean deleteTask(int pTaskId, int pUserId, int pStudentId) {
        return getTaskRepository().deleteTask(pTaskId, pUserId, pStudentId);
    }

    public TaskStudentsRelationship queryTaskStudentRelationship(int pTaskId, int pStudentId) {
        return getTaskRepository().queryTaskStudentRelationship(pTaskId, pStudentId);
    }

    public ITaskRepository getTaskRepository() {
        return mTaskRepository;
    }

    public void setTaskRepository(ITaskRepository pTaskRepository) {
        mTaskRepository = pTaskRepository;
    }
}
