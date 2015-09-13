package com.fmc.edu.manager;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.service.impl.MyAccountService;
import com.fmc.edu.service.impl.TeacherService;
import com.fmc.edu.util.RepositoryUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "teacherManager")
public class TeacherManager {

    private static final Logger LOG = Logger.getLogger(TeacherManager.class);

    @Resource(name = "teacherService")
    private TeacherService mTeacherService;

    @Resource(name = "myAccountService")
    private MyAccountService mMyAccountService;

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
            throw new ProfileException(ResourceManager.VALIDATION_USER_TEACHER_ID_ERROR);
        }
        if (getMyAccountService().checkPhoneExist(pTeacher.getId(), pTeacher.getPhone())) {
            throw new ProfileException(ResourceManager.VALIDATION_USER_PHONE_EXIST);
        }
        return getTeacherService().updateTeacher(pTeacher);
    }

    public List<TeacherProfile> queryTeachersBySchoolId(int pSchoolId) {
        return getTeacherService().queryTeachersBySchoolId(pSchoolId);
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

    public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pTeacherId) {
        return getTeacherService().queryTeacherClassRelationships(pTeacherId);
    }

    public boolean persistTeacherDetail(final TeacherProfile pTeacher) {
        boolean result;
        if (RepositoryUtils.idIsValid(pTeacher.getId())) {
            LOG.debug("Persist teacher changes by doing update.");
            //TODO Add logic to set initial password/salt for teacher
            result = getTeacherService().updateTeacherDetail(pTeacher);
        } else {
            LOG.debug("Persist teacher changes by doing creation.");
            result = getTeacherService().createTeacherDetail(pTeacher) > 0;
        }
        LOG.debug(String.format("Result of process persist teacher: %s", result));
        return result;
    }

    public int createTeacherDetail(final TeacherProfile pTeacher) {

        return getTeacherService().createTeacherDetail(pTeacher);
    }

    public MyAccountService getMyAccountService() {
        return mMyAccountService;
    }

    public void setMyAccountService(final MyAccountService pMyAccountService) {
        mMyAccountService = pMyAccountService;
    }

    public int createTeacherClassRelationship(TeacherClassRelationship pTeacherClassRelationship) {
        return getTeacherService().createTeacherClassRelationship(pTeacherClassRelationship);
    }

    public List<TeacherProfile> queryTeacherNotInClass(final int pClassId) {
        return getTeacherService().queryTeacherNotInClass(pClassId);
    }
}
