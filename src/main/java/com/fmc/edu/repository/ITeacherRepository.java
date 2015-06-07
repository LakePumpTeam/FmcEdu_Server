package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
public interface ITeacherRepository {

    String QUERY_TEACHER_BY_ID = "com.fmc.edu.teacher.queryTeacherById";

    TeacherProfile queryTeacherById(final int pTeacherId);

    String QUERY_HEADER_TEACHER_BY_PARENT_ID = "com.fmc.edu.teacher.queryHeaderTeacherByParentId";

    List<Map<String, Object>> queryHeaderTeacherByParentId(final int parentId);

    String QUERY_CLASS_BY_TEACHER_ID = "com.fmc.edu.teacher.queryClassByTeacherId";

    List<Map<String, Object>> queryClassByTeacherId(int pTeacherId);

    String UPDATE_TEACHER = "com.fmc.edu.teacher.updateTeacher";

    boolean updateTeacher(TeacherProfile pTeacher);

    String QUERY_CLASS_BY_ID = "com.fmc.edu.teacher.queryClassById";

    FmcClass queryClassById(int pClassId);
}
