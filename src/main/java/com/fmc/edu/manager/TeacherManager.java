package com.fmc.edu.manager;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.service.impl.TeacherService;
import org.springframework.stereotype.Service;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "teacherManager")
public class TeacherManager {

    private TeacherService mTeacherService;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherService().queryTeacherById(pTeacherId);
    }


    public TeacherService getTeacherService() {
        return mTeacherService;
    }

    public void setTeacherService(TeacherService pTeacherService) {
        mTeacherService = pTeacherService;
    }
}
