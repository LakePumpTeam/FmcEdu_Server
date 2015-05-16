package com.fmc.edu.manager;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.service.impl.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "teacherManager")
public class TeacherManager {

    @Resource(name = "teacherService")
    private TeacherService mTeacherService;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherService().queryTeacherById(pTeacherId);
    }

    public List<Map<String, Object>> queryHeaderTeacherByParentId(int parentId) {
        return getTeacherService().queryHeaderTeacherByParentId(parentId);
    }

    public Map<String, Object> queryClassByTeacherId(int pTeacherId) {
        return getTeacherService().queryClassByTeacherId(pTeacherId);
    }
    public TeacherService getTeacherService() {
        return mTeacherService;
    }

    public void setTeacherService(TeacherService pTeacherService) {
        mTeacherService = pTeacherService;
    }
}
