package com.fmc.edu.manager;

import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.impl.StudentService;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "studentManager")
public class StudentManager {

	@Resource(name = "studentService")
	private StudentService mStudentService;

	public List<Student> queryStudentByParentId(int pParentId) {
		return getStudentService().queryStudentByParentId(pParentId);
	}

	public Map<String, Object> queryStudentListByTeacherId(int pTeacherId) {
		return getStudentService().queryStudentListByTeacherId(pTeacherId);
	}

	public Student queryStudentById(int pStudentId) {
		return getStudentService().queryStudentById(pStudentId);
	}

	public Map<String, Object> queryStudentsByClassId(final int pClassId) {
		return getStudentService().queryStudentsByClassId(pClassId);
	}

	public List<ParentStudentRelationship> queryParentStudentRelationshipByStudentId(final int pStudentId) {
		return getStudentService().queryParentStudentRelationshipByStudentId(pStudentId);
	}
	public StudentService getStudentService() {
		return mStudentService;
	}

	public void setStudentService(StudentService pStudentService) {
		mStudentService = pStudentService;
	}

	public List<Student> loadClassStudents(final int pClassId) {
		return getStudentService().loadClassStudents(pClassId);
	}

	public Student queryStudentByIdentifyCode(String pIdentifyCode) {
		return getStudentService().queryStudentByIdentifyCode(pIdentifyCode);
	}

	public boolean persistStudent(final Student pStudent) {
		if (RepositoryUtils.idIsValid(pStudent.getId())) {
			return getStudentService().updateStudent(pStudent);
		} else {
			return getStudentService().createStudent(pStudent);
		}
	}
}
