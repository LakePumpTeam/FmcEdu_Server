package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;
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
    public static final String ERROR_NOT_FOUND_TEACHER = "老师不存在.";

    @Resource(name = "teacherService")
    private TeacherService mTeacherService;

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherService().queryTeacherById(pTeacherId);
    }

    public List<Map<String, Object>> queryHeaderTeacherByParentId(int parentId) {
        return getTeacherService().queryHeaderTeacherByParentId(parentId);
    }

    public List<Map<String, Object>> queryClassByTeacherId(int pTeacherId) {
        return getTeacherService().queryClassByTeacherId(pTeacherId);
    }

    public boolean updateTeacher(final TeacherProfile pTeacher) throws ProfileException {
        if (queryTeacherById(pTeacher.getId()) == null) {
            throw new ProfileException(TeacherManager.ERROR_NOT_FOUND_TEACHER);
        }
        return getTeacherService().updateTeacher(pTeacher);
    }

    public FmcClass queryClassById(int pClassId) {
        return getTeacherService().queryClassById(pClassId);
    }

    public TeacherService getTeacherService() {
        return mTeacherService;
    }

    public void setTeacherService(TeacherService pTeacherService) {
        mTeacherService = pTeacherService;
    }
}
