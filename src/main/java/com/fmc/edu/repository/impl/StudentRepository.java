package com.fmc.edu.repository.impl;

import com.fmc.edu.model.student.Student;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IStudentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository(value = "studentRepository")
public class StudentRepository extends BaseRepository implements IStudentRepository {

    @Override
    public List<Student> queryStudentByParentId(int pParentId) {
        return getSqlSession().selectList(QUERY_STUDENTS_BY_PARENT_ID, pParentId);
    }
}
