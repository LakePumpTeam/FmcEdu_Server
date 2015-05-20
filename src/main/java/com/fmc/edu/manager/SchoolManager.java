package com.fmc.edu.manager;

import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.impl.SchoolService;
import com.fmc.edu.util.NumberUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "schoolManager")
public class SchoolManager {

    @Resource(name = "schoolService")
    private SchoolService mSchoolService;

    public Map<String, Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        return getSchoolService().querySchoolsPage(pPagination, pCityId, pKey);
    }

    public Map<String, Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        Map<String, Object> classes = getSchoolService().queryClassesPage(pPagination, pSchoolId, pKey);
        List<Map<String, String>> queryResult = (List<Map<String, String>>) classes.get("classList");
        for (Map<String, String> res : queryResult) {
            res.put("className", getClassString(String.valueOf(res.get("grade")), String.valueOf(res.get("class"))));
        }
        return classes;
    }

    public Map<String, Object> queryHeadmasterPage(final int pClassId) {
        return getSchoolService().queryHeadmasterPage(pClassId);
    }

    public String getClassString(String grade, String cls) {
        if (StringUtils.isBlank(grade) || StringUtils.isBlank(cls)) {
            return "";
        }
        int year = Integer.valueOf(grade);
        Calendar calendar = Calendar.getInstance();
        int gradeY = calendar.get(Calendar.YEAR) - year;
        int month = calendar.get(Calendar.MONTH);
        if (month > 7) {
            gradeY++;
        }
        if (gradeY == 0) {
            gradeY++;
        }
        StringBuilder convertedClass = new StringBuilder();
        convertedClass.append(NumberUtils.numberToChineseNumber(gradeY))
                .append("年级").append(NumberUtils.numberToChineseNumber(Integer.valueOf(cls)))
                .append("班");
        return convertedClass.toString();
    }

    public boolean persistStudent(final Student pStudent) {
        if (RepositoryUtils.idIsValid(pStudent.getId())) {
            return getSchoolService().updateStudentById(pStudent);
        }
        return getSchoolService().saveOrUpdateStudentByFields(pStudent);
    }

    public int queryStudentIdByFields(final Student pStudent) {
        return getSchoolService().queryStudentIdByFields(pStudent);
    }

    public TeacherProfile queryTeacherById(final int pTeacherId) {
        return getSchoolService().queryTeacherById(pTeacherId);
    }

    public SchoolService getSchoolService() {
        return mSchoolService;
    }

    public void setSchoolService(SchoolService pSchoolService) {
        this.mSchoolService = pSchoolService;
    }
}
