package com.fmc.edu.web;

import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.StudentManager;
import com.fmc.edu.manager.TeacherManager;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "responseBuilder")
public class ResponseBuilder {

    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    public void buildAuthorizedResponse(Map<String, Object> pResponseMap, final BaseProfile pProfile) {
        pResponseMap.put("userId", pProfile.getId());
        pResponseMap.put("userRole", pProfile.getProfileType());

        if (pProfile.getProfileType() == ProfileType.PARENT) {
            ParentProfile parentProfile = getProfileManager().queryParentByPhone(pProfile.getPhone());
            //TODO need to modify.
            List<Student> studentList = getStudentManager().queryStudentByParentId(parentProfile.getId());
            if (!CollectionUtils.isEmpty(studentList)) {
                Student student = studentList.get(0);
                pResponseMap.put("auditState", student.getParentStudentRelationship().isApproved());
                pResponseMap.put("userCardNum", student.getRingPhone());
                pResponseMap.put("studentName", student.getName());
                pResponseMap.put("studentSex", student.isMale());
                pResponseMap.put("schoolName", student.getSchool().getName());
                pResponseMap.put("repayState", parentProfile.isPaid());
            }
        } else {
            TeacherProfile teacherProfile = getTeacherManager().queryTeacherById(pProfile.getId());
            if (teacherProfile == null) {
                return;
            }
            pResponseMap.put("schoolName", teacherProfile.getSchool().getName());
        }

    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(TeacherManager pTeacherManager) {
        mTeacherManager = pTeacherManager;
    }
}
