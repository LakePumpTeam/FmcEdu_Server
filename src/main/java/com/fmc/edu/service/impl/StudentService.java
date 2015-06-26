package com.fmc.edu.service.impl;

import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.IStudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> queryStudentListByTeacherId(int pTeacherId) {
        return getStudentRepository().queryStudentListByTeacherId(pTeacherId);
    }

    public Student queryStudentById(int pStudentId) {
        return getStudentRepository().queryStudentById(pStudentId);
    }

    public Map<String, Object> queryStudentsByClassId(final int pClassId) {
        return getStudentRepository().queryStudentsByClassId(pClassId);
    }

    public IStudentRepository getStudentRepository() {
        return mStudentRepository;
    }

    public void setStudentRepository(IStudentRepository pStudentRepository) {
        mStudentRepository = pStudentRepository;
    }
}
