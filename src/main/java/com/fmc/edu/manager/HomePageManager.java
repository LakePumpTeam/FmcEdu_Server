package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/16.
 */
@Service("homePageManager")
public class HomePageManager {
    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    public Map<String, Object> obtainHeaderTeacher(final String pProfileId) throws ProfileException {
        BaseProfile baseProfile = getMyAccountManager().findUserById(pProfileId);
        if (baseProfile == null) {
            throw new ProfileException("用户不存在.");
        }
        Map<String, Object> headerTeacher = new HashMap<String, Object>();
        headerTeacher.put("profileId", baseProfile.getId());
        headerTeacher.put("userRole", baseProfile.getProfileType());

        if (baseProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            List<Map<String, Object>> headerTeachers = getTeacherManager().queryHeaderTeacherByParentId(baseProfile.getId());
            Map<String, Object> teacher;
            if (CollectionUtils.isEmpty(headerTeachers)) {
                teacher = new HashMap<>(0);
            } else {
                teacher = headerTeachers.get(0);
            }
            headerTeacher.put("teacherName", teacher.get("teacherName"));
            headerTeacher.put("sex", teacher.get("sex"));
            headerTeacher.put("className", getSchoolManager().getClassString(String.valueOf(teacher.get("grade")), String.valueOf(teacher.get("class"))));
        } else if (baseProfile.getProfileType() == ProfileType.TEACHER.getValue()) {
            headerTeacher.put("teacherName", baseProfile.getName());
            TeacherProfile teacher = getTeacherManager().queryTeacherById(baseProfile.getId());
            if (teacher == null) {
                teacher = new TeacherProfile();
            }
            headerTeacher.put("sex", teacher.isMale());
            Map<String, Object> tClass = getTeacherManager().queryClassByTeacherId(baseProfile.getId());
            if (tClass == null) {
                tClass = new HashMap<>(0);
            }
            headerTeacher.put("className", getSchoolManager().getClassString(String.valueOf(tClass.get("grade")), String.valueOf(tClass.get("class"))));
        }
        return headerTeacher;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        this.mMyAccountManager = pMyAccountManager;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(TeacherManager pTeacherManager) {
        this.mTeacherManager = pTeacherManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        this.mSchoolManager = pSchoolManager;
    }
}
