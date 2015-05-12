package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.repository.ITeacherRepository;
import org.springframework.stereotype.Service;

/**
 * Created by dylanyu on 5/12/2015.
 */
@Service("teacherService")
public class TeacherService {
    private ITeacherRepository mTeacherRepository;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherRepository().queryTeacherById(pTeacherId);
    }

    public ITeacherRepository getTeacherRepository() {
        return mTeacherRepository;
    }

    public void setTeacherRepository(ITeacherRepository pTeacherRepository) {
        mTeacherRepository = pTeacherRepository;
    }
}
