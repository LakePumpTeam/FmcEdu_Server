package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by silly on 2015/5/10.
 */
@Service("schoolService")
public class SchoolService {

    @Resource(name = "schoolRepository")
    private ISchoolRepository mSchoolRepository;

    public Map<String, Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        return getSchoolRepository().querySchoolsPage(pPagination, pCityId, pKey);
    }

    public Map<String, Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        return getSchoolRepository().queryClassesPage(pPagination, pSchoolId, pKey);
    }

    public Map<String, Object> queryHeadmasterPage(final int pClassId) {
        return getSchoolRepository().queryHeadmasterPage(pClassId);
    }

    public boolean updateStudentById(final Student pStudent) {
        return getSchoolRepository().updateStudentById(pStudent);
    }

    public int queryStudentIdByFields(final Student pStudent) {
        return getSchoolRepository().queryStudentIdByFields(pStudent);
    }
    public boolean saveOrUpdateStudentByFields(final Student pStudent) {
        int id = getSchoolRepository().queryStudentIdByFields(pStudent);
        if (id > 0) {
            pStudent.setId(id);
            return updateStudentById(pStudent);
        }
        return getSchoolRepository().initialStudent(pStudent);
    }

    public TeacherProfile queryTeacherById(final int pTeacherId) {
        return getSchoolRepository().queryTeacherById(pTeacherId);
    }

    public ISchoolRepository getSchoolRepository() {
        return mSchoolRepository;
    }

    public void setSchoolRepository(ISchoolRepository schoolRepository) {
        this.mSchoolRepository = schoolRepository;
    }
}
