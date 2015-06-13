package com.fmc.edu.repository;

import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/31/2015.
 */
public interface ITaskRepository {
    String PUBLISH_TASK = "com.fmc.edu.task.publishTask";

    boolean publishTask(Task pTask);

    String INSERT_TASK_STUDENT_RELATIONSHIP = "com.fmc.edu.task.insertTaskStudentRelationship";

    boolean insertTaskStudentRelationship(List<TaskStudentsRelationship> pTaskStudentsRelationships);

    String EDIT_TASK = "com.fmc.edu.task.editTask";

    boolean editTask(Task pTask);

    String SUBMIT_TASK = "com.fmc.edu.task.submitTask";

    boolean submitTask(int pTaskId, int pStudentId);

    String QUERY_TASK_LIST_BY_PARENT_ID = "com.fmc.edu.task.queryTaskListByParentId";

    Map<String, Object> queryTaskListByParentId(int pParentId, String pFilter, Integer pStatus, Pagination pPagination);

    String QUERY_TASK_LIST_BY_TEACHER_ID = "com.fmc.edu.task.queryTaskListByTeacherId";

    Map<String, Object> queryTaskListByTeacherId(int pTeacherId, String pFilter, Integer pStatus, Pagination pPagination);

    String QUERY_TASK_DETAIL = "com.fmc.edu.task.queryTaskDetail";

    Task queryTaskDetail(int pTaskId);

    String DELETE_TASK = "com.fmc.edu.task.deleteTask";

    boolean deleteTask(int pTaskId, int pUserId, int pStudentId);

    String QUERY_TASK_STUDENT_RELATIONSHIP_BY_STUDENT_ID_AND_TASK_ID = "com.fmc.edu.task.queryTaskStudentRelationship";

    TaskStudentsRelationship queryTaskStudentRelationship(int pTaskId, int pStudentId);
}
