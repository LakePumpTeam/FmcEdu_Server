package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.StringUtils;
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

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    public Map<String, Object> obtainHeaderTeacher(final String pProfileId,
                                                   final String pOptionId) throws ProfileException {
        BaseProfile baseProfile = getMyAccountManager().findUserById(pProfileId);
        if (baseProfile == null) {
            throw new ProfileException(ResourceManager.ERROR_NOT_FIND_USER, pProfileId);
        }
        Map<String, Object> headerTeacher = new HashMap<String, Object>();
        headerTeacher.put("userId", baseProfile.getId());
        headerTeacher.put("userRole", baseProfile.getProfileType());

        if (baseProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            Student student = getStudentManager().queryStudentById(Integer.valueOf(pOptionId));
            List<Map<String, Object>> headerTeachers = getTeacherManager().queryHeaderTeacherByParentId(baseProfile.getId());
            Map<String, Object> teacher;
            if (CollectionUtils.isEmpty(headerTeachers)) {
                teacher = new HashMap<String, Object>(0);
            } else {
                teacher = headerTeachers.get(0);
            }
            if (student != null) {
                headerTeacher.put("sex", student.isMale());
            }
            headerTeacher.put("teacherId", teacher.get("teacherId"));
            headerTeacher.put("teacherName", StringUtils.ifNULLReturn(teacher.get("teacherName"), ""));
            headerTeacher.put("className", StringUtils.ifNULLReturn(getSchoolManager().getClassString(String.valueOf(teacher.get("grade")), String.valueOf(teacher.get("class"))), ""));
        } else if (baseProfile.getProfileType() == ProfileType.TEACHER.getValue()) {
            headerTeacher.put("teacherId", baseProfile.getId());
            headerTeacher.put("teacherName", StringUtils.ifNULLReturn(baseProfile.getName(), ""));
            TeacherProfile teacher = getTeacherManager().queryTeacherById(baseProfile.getId());
            if (teacher == null) {
                teacher = new TeacherProfile();
            }
            headerTeacher.put("sex", teacher.isMale());
            FmcClass tClass = getTeacherManager().queryClassById(Integer.valueOf(pOptionId));
            if (tClass == null) {
                headerTeacher.put("className", "");
            } else {
                headerTeacher.put("className", StringUtils.ifNULLReturn(getSchoolManager().getClassString(String.valueOf(tClass.getGrade()), String.valueOf(tClass.getClass())), ""));
            }
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

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }
}
