package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository("teacherRepository")
public class TeacherRepository extends BaseRepository implements ITeacherRepository {
    @Override
    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getSqlSession().selectOne(QUERY_TEACHER_BY_ID, pTeacherId);
    }

    @Override
    public List<Map<String, Object>> queryHeaderTeacherByParentId(int pTeacherId) {
        return getSqlSession().selectList(QUERY_HEADER_TEACHER_BY_PARENT_ID, pTeacherId);
    }

    @Override
    public List<Map<String, Object>> queryClassByTeacherId(int pTeacherId) {
        return getSqlSession().selectOne(QUERY_CLASS_BY_TEACHER_ID, pTeacherId);
    }

    @Override
    public boolean updateTeacher(final TeacherProfile pTeacher) {
        return getSqlSession().update(UPDATE_TEACHER, pTeacher) > 0;
    }

    @Override
    public FmcClass queryClassById(int pClassId) {
        return getSqlSession().selectOne(QUERY_CLASS_BY_ID, pClassId);
    }
}
