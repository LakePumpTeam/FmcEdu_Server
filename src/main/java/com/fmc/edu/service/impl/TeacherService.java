package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.repository.ITeacherRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by dylanyu on 5/12/2015.
 */
@Service("teacherService")
public class TeacherService {

    private static final Logger LOG = Logger.getLogger(TeacherService.class);

    @Resource(name = "teacherRepository")
    private ITeacherRepository mTeacherRepository;

    public ITeacherRepository getTeacherRepository() {
        return mTeacherRepository;
    }

    public void setTeacherRepository(ITeacherRepository pTeacherRepository) {
        mTeacherRepository = pTeacherRepository;
    }

    public TeacherProfile queryTeacherById(int pTeacherId) {
        return getTeacherRepository().queryTeacherById(pTeacherId);
    }

    public List<Map<String, Object>> queryHeaderTeacherByParentId(int parentId) {
        return getTeacherRepository().queryHeaderTeacherByParentId(parentId);
    }

    public List<Map<String, Object>> queryClassByTeacherId(int pTeacherId) {
        return getTeacherRepository().queryClassByTeacherId(pTeacherId);
    }

    public boolean updateTeacher(final TeacherProfile pTeacher) {
        return getTeacherRepository().updateTeacher(pTeacher);
    }

    public FmcClass queryClassById(int pClassId) {
        return getTeacherRepository().queryClassById(pClassId);
    }

    public List<TeacherProfile> queryTeachersBySchoolId(final int pSchoolId) {
        return getTeacherRepository().queryTeachersBySchoolId(pSchoolId);
    }

    public List<TeacherClassRelationship> queryTeacherClassRelationships(final int pTeacherId) {
        return getTeacherRepository().queryTeacherClassRelationships(pTeacherId);
    }

    public boolean updateTeacherDetail(final TeacherProfile pTeacher) {
        boolean result = getTeacherRepository().updateTeacherProfile(pTeacher);
        LOG.debug(String.format("Result of process update teacher profile: %s", result));
        if (result) {
            getTeacherRepository().updateTeacherDetail(pTeacher);
            LOG.debug(String.format("Result of process update teacher detail: %s", result));
        }
        return result;
    }

    public int createTeacherDetail(final TeacherProfile pTeacher) {
        int profileId = getTeacherRepository().createTeacherProfile(pTeacher);
        LOG.debug(String.format("Result of process create teacher profile: %s", profileId));
        if (RepositoryUtils.idIsValid(profileId)) {
            pTeacher.setId(profileId);
            getTeacherRepository().createTeacherDetail(pTeacher);
            LOG.debug(String.format("Result of process create teacher detail: %s", profileId));
        }
        return profileId;
    }

    public int createTeacherClassRelationship(TeacherClassRelationship pTeacherClassRelationship) {
        return getTeacherRepository().createTeacherClassRelationship(pTeacherClassRelationship);
    }

	public boolean maintainHeadTeacherRelationship(final int pClassId, final int pTeacherId) {
		LOG.debug(String.format("Reset all head teacher to false for class: %d", pClassId));
		getTeacherRepository().resetAllHeadTeacherRelationship(pClassId);
		boolean result = getTeacherRepository().updateHeadTeacherRelationship(pClassId, pTeacherId);
		LOG.debug(String.format("Result of process to update head teacher on class / relationship: %s", result));
		return result;
	}

    public List<TeacherProfile> queryTeacherNotInClass(final int pClassId) {
        return getTeacherRepository().queryTeacherNotInClass(pClassId);
    }

    public boolean updateTeacherClassRelationship(final TeacherClassRelationship pRelationship) {
        return getTeacherRepository().updateTeacherClassRelationship(pRelationship);
    }

    public boolean updateTeacherClassRelationshipAvailableBatch(final TeacherClassRelationship[] pRelationships) {
        return getTeacherRepository().updateTeacherClassRelationshipAvailableBatch(pRelationships);
    }
}
