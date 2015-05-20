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

    public void buildAuthorizedResponse(ResponseBean pResponseBean, final BaseProfile pProfile) {
        pResponseBean.addData("userId", pProfile.getId());
        pResponseBean.addData("userRole", pProfile.getProfileType());
        pResponseBean.addData("salt", pProfile.getSalt());

        if (pProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            ParentProfile parentProfile = getProfileManager().queryParentByPhone(pProfile.getPhone());
            //TODO need to modify.
            List<Student> studentList = getStudentManager().queryStudentByParentId(parentProfile.getId());
            if (!CollectionUtils.isEmpty(studentList)) {
                Student student = studentList.get(0);
                pResponseBean.addData("auditState", student.getParentStudentRelationship().getApproved());
                pResponseBean.addData("userCardNum", student.getRingPhone());
                pResponseBean.addData("studentName", student.getName());
                pResponseBean.addData("studentSex", student.isMale());
                pResponseBean.addData("schoolName", student.getSchool().getName());
                pResponseBean.addData("repayState", parentProfile.isPaid());
            }
        } else {
            TeacherProfile teacherProfile = getTeacherManager().queryTeacherById(pProfile.getId());
            if (teacherProfile == null) {
                return;
            }
            pResponseBean.addData("schoolName", teacherProfile.getSchool().getName());
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
