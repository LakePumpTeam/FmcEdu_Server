package com.fmc.edu.manager;

import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.impl.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "studentManager")
public class StudentManager {
    private StudentService mStudentService;

    public List<Student> queryStudentByParentId(int pParentId) {
        return getStudentService().queryStudentByParentId(pParentId);
    }

    public StudentService getStudentService() {
        return mStudentService;
    }

    public void setStudentService(StudentService pStudentService) {
        mStudentService = pStudentService;
    }
}
