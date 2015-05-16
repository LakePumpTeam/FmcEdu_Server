package com.fmc.edu.service.impl;

import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.IStudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yu on 5/12/2015.
 */
@Service("studentService")
public class StudentService {
    @Resource(name = "studentRepository")
    private IStudentRepository mStudentRepository;

    public List<Student> queryStudentByParentId(int pParentId) {
        return getStudentRepository().queryStudentByParentId(pParentId);
    }

    public IStudentRepository getStudentRepository() {
        return mStudentRepository;
    }

    public void setStudentRepository(IStudentRepository pStudentRepository) {
        mStudentRepository = pStudentRepository;
    }
}
