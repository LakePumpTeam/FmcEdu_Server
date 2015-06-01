package com.fmc.edu.manager;

import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.service.impl.TaskService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/31/2015.
 */
@Service("taskManager")
public class TaskManager {
    @Resource(name = "taskService")
    private TaskService mTaskService;

    public boolean publishTask(Task pTask) {
        return getTaskService().publishTask(pTask);
    }

    public boolean insertTaskStudentRelationship(List<TaskStudentsRelationship> pTaskStudentsRelationships) {
        return getTaskService().insertTaskStudentRelationship(pTaskStudentsRelationships);
    }

    public boolean editTask(Task pTask) {
        return getTaskService().editTask(pTask);
    }

    public boolean submitTask(int pTaskId, int pStudentId) {
        return getTaskService().submitTask(pTaskId, pStudentId);
    }

    public Map<String, Object> queryTaskListByParentId(int pParentId, String pFilter, int pStatus, Pagination pPagination) {
        return getTaskService().queryTaskListByParentId(pParentId, pFilter, pStatus, pPagination);
    }

    public Map<String, Object> queryTaskListByTeacherId(int pTeacherId, String pFilter, int pStatus, Pagination pPagination) {
        return getTaskService().queryTaskListByTeacherId(pTeacherId, pFilter, pStatus, pPagination);
    }

    public Task queryTaskDetail(int pTaskId) {
        return getTaskService().queryTaskDetail(pTaskId);
    }

    public boolean deleteTask(int pTaskId, int pUserId, int pStudentId) {
        return getTaskService().deleteTask(pTaskId, pUserId, pStudentId);
    }

    public TaskService getTaskService() {
        return mTaskService;
    }

    public void setTaskService(TaskService pTaskService) {
        mTaskService = pTaskService;
    }
}
