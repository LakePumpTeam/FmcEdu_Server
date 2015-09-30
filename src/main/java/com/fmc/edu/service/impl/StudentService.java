package com.fmc.edu.service.impl;

import com.fmc.edu.model.relationship.ParentStudentRelationship;
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

    public List<ParentStudentRelationship> queryParentStudentRelationshipByStudentId(final int pStudentId) {
        return getStudentRepository().queryParentStudentRelationshipByStudentId(pStudentId);
    }
    public IStudentRepository getStudentRepository() {
        return mStudentRepository;
    }

    public void setStudentRepository(IStudentRepository pStudentRepository) {
        mStudentRepository = pStudentRepository;
    }

    public List<Student> loadClassStudents(final int pClassId) {
        return getStudentRepository().loadClassStudents(pClassId);
    }

    public Student queryStudentByIdentifyCode(String pIdentifyCode) {
        return getStudentRepository().queryStudentByIdentifyCode(pIdentifyCode);
    }

    public boolean updateStudent(final Student pStudent) {
        return getStudentRepository().updateStudent(pStudent);
    }

    public boolean createStudent(final Student pStudent) {
        return getStudentRepository().createStudent(pStudent);
    }
}
