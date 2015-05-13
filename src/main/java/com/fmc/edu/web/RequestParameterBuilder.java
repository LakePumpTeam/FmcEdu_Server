package com.fmc.edu.web;

import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "requestParameterBuilder")
public class RequestParameterBuilder {

	@Resource(name = "replacementBase64EncryptService")
	private ReplacementBase64EncryptService mBase64EncryptService;

	public Student buildStudent(HttpServletRequest pRequest) throws IOException, ParseException {
		String name = mBase64EncryptService.decrypt(pRequest.getParameter("studentName"));
		String sexString = mBase64EncryptService.decrypt(pRequest.getParameter("studentSex"));
		String birthString = mBase64EncryptService.decrypt(pRequest.getParameter("studentAge"));
		String ringNum = mBase64EncryptService.decrypt(pRequest.getParameter("braceletNumber"));
		String ringPhone = mBase64EncryptService.decrypt(pRequest.getParameter("braceletCardNumber"));
		String classId = mBase64EncryptService.decrypt(pRequest.getParameter("classId"));
		Student stu = new Student(Integer.valueOf(classId), name);
		//TODO update after confirmation
		stu.setMale(Boolean.valueOf(sexString));
		stu.setBirth(DateUtils.getStudentBirth(birthString));
		stu.setRingNumber(ringNum);
		stu.setRingPhone(ringPhone);
		stu.setAvailable(Boolean.TRUE);
		return stu;
	}

	public Address buildAddress(HttpServletRequest pRequest) throws IOException {
		//TODO update after confirmation
		String fullAddress = mBase64EncryptService.decrypt(pRequest.getParameter("address"));
		if (StringUtils.isBlank(fullAddress)) {
			return null;
		}
		String provinceId = mBase64EncryptService.decrypt(pRequest.getParameter("provId"));
		String cityId = mBase64EncryptService.decrypt(pRequest.getParameter("cityId"));
		Address address = new Address(Integer.valueOf(provinceId), Integer.valueOf(cityId), fullAddress);
		return address;
	}

	public ParentStudentRelationship buildParentStudentRelationship(HttpServletRequest pRequest) throws IOException {
		String phone = mBase64EncryptService.decrypt(pRequest.getParameter("cellPhone"));
		String relationship = mBase64EncryptService.decrypt(pRequest.getParameter("relation"));
		return new ParentStudentRelationship(phone, relationship);
	}

	public ParentProfile buildParent(HttpServletRequest pRequest) {
		ParentProfile parent = new ParentProfile();
		String phone = getBase64EncryptService().decrypt(pRequest.getParameter("cellPhone"));
		String parentName = getBase64EncryptService().decrypt(pRequest.getParameter("parentName"));
		parent.setPhone(phone);
		parent.setName(parentName);
		return parent;

	}

	public ReplacementBase64EncryptService getBase64EncryptService() {
		return mBase64EncryptService;
	}

	public void setBase64EncryptService(final ReplacementBase64EncryptService pBase64EncryptService) {
		mBase64EncryptService = pBase64EncryptService;
	}
}
