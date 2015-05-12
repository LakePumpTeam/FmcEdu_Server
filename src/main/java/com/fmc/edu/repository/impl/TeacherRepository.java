package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository("teacherRepository")
public class TeacherRepository extends BaseRepository implements ITeacherRepository {
    @Override
    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getSqlSession().selectOne(QUERY_TEACHER_BY_ID, pTeacherId);
    }
}
