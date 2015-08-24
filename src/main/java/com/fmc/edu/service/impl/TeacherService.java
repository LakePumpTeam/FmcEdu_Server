package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by dylanyu on 5/12/2015.
 */
@Service("teacherService")
public class TeacherService {

    @Resource(name = "teacherRepository")
    private ITeacherRepository mTeacherRepository;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherRepository().queryTeacherById(pTeacherId);
    }


    public List<Map<String, Object>> queryHeaderTeacherByParentId(int parentId) {
        return getTeacherRepository().queryHeaderTeacherByParentId(parentId);
    }

    public List<Map<String, Object>> queryClassByTeacherId(int pTeacherId) {
        return getTeacherRepository().queryClassByTeacherId(pTeacherId);
    }

    public boolean updateTeacher(final TeacherProfile pTeacher) {
        return getTeacherRepository().updateTeacher(pTeacher);
    }

    public FmcClass queryClassById(int pClassId) {
        return getTeacherRepository().queryClassById(pClassId);
    }

    public ITeacherRepository getTeacherRepository() {
        return mTeacherRepository;
    }

    public void setTeacherRepository(ITeacherRepository pTeacherRepository) {
        mTeacherRepository = pTeacherRepository;
    }

    public List<TeacherProfile> queryTeachersBySchoolId(final int pSchoolId) {
        return getTeacherRepository().queryTeachersBySchoolId(pSchoolId);
    }

    public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pTeacherId) {
        return getTeacherRepository().queryTeacherClassRelationships(pTeacherId);
    }
}
