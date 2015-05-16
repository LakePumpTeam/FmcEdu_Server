package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dylanyu on 5/12/2015.
 */
@Service("teacherService")
public class TeacherService {
    private ITeacherRepository mTeacherRepository;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherRepository().queryTeacherById(pTeacherId);
    }


    public List<Map<String, Object>> queryHeaderTeacherByParentId(int parentId) {
        return getTeacherRepository().queryHeaderTeacherByParentId(parentId);
    }

    public Map<String, Object> queryClassByTeacherId(int pTeacherId) {
        return getTeacherRepository().queryClassByTeacherId(pTeacherId);
    }

    public ITeacherRepository getTeacherRepository() {
        return mTeacherRepository;
    }

    public void setTeacherRepository(ITeacherRepository pTeacherRepository) {
        mTeacherRepository = pTeacherRepository;
    }
}
