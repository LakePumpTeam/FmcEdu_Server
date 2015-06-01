package com.fmc.edu.repository.impl;

import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITaskRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/31/2015.
 */
@Repository("taskRepository")
public class TaskRepository extends BaseRepository implements ITaskRepository {
    @Override
    public boolean publishTask(Task pTask) {
        return getSqlSession().insert(PUBLISH_TASK, pTask) > 0;
    }

    @Override
    public boolean insertTaskStudentRelationship(List<TaskStudentsRelationship> pTaskStudentsRelationships) {
        return getSqlSession().insert(INSERT_TASK_STUDENT_RELATIONSHIP, pTaskStudentsRelationships) > 0;
    }

    @Override
    public boolean editTask(Task pTask) {
        return getSqlSession().update(EDIT_TASK, pTask) > 0;
    }

    @Override
    public boolean submitTask(int pTaskId, int pStudentId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("taskId", pTaskId);
        params.put("studentId", pStudentId);
        return getSqlSession().update(SUBMIT_TASK, params) > 0;
    }

    @Override
    public Map<String, Object> queryTaskListByParentId(int pParentId, String pFilter, int pStatus, Pagination pPagination) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("parentId", pParentId);
        params.put("filter", pFilter);
        params.put("status", pStatus);
        List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_TASK_LIST_BY_PARENT_ID, params);
        Map<String, Object> dataList = new HashMap<String, Object>(2);
        dataList.put("taskList", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public Map<String, Object> queryTaskListByTeacherId(int pTeacherId, String pFilter, int pStatus, Pagination pPagination) {
        Map<String, Object> params = paginationToParameters(pPagination);
        params.put("teacherId", pTeacherId);
        params.put("filter", pFilter);
        params.put("status", pStatus);
        List<Map<String, String>> queryResult = getSqlSession().selectList(QUERY_TASK_LIST_BY_TEACHER_ID, params);

        Map<String, Object> dataList = new HashMap<String, Object>(2);
        dataList.put("taskList", queryResult);
        addIsLastPageFlag(dataList, queryResult, pPagination.getPageSize());
        return dataList;
    }

    @Override
    public Task queryTaskDetail(int pTaskId) {
        return getSqlSession().selectOne(QUERY_TASK_DETAIL, pTaskId);
    }

    @Override
    public boolean deleteTask(int pTaskId, int pUserId, int pStudentId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("taskId", pTaskId);
        params.put("userId", pUserId);
        params.put("studentId", pStudentId);
        return getSqlSession().delete(DELETE_TASK, params) > 0;
    }
}