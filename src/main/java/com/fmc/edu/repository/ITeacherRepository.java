package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TeacherProfile;

/**
 * Created by Yu on 5/12/2015.
 */
public interface ITeacherRepository {

    String QUERY_TEACHER_BY_ID = "com.fmc.edu.teacher.queryTeacherById";

    TeacherProfile queryTeacherById(final int pTeacherId);
}
