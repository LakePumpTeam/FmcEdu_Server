package com.fmc.edu.web;

import com.fmc.edu.crypto.impl.Base64CryptoService;
import com.fmc.edu.model.address.Address;
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
	@Resource(name = "base64CryptoService")
	private Base64CryptoService mBase64CryptoService;

	public Student buildStudent(HttpServletRequest pRequest) throws IOException, ParseException {
		String name = mBase64CryptoService.decrypt(pRequest.getParameter("studentName"));
		String sexString = mBase64CryptoService.decrypt(pRequest.getParameter("studentSex"));
		String birthString = mBase64CryptoService.decrypt(pRequest.getParameter("studentAge"));
		String ringNum = mBase64CryptoService.decrypt(pRequest.getParameter("braceletNumber"));
		String ringPhone = mBase64CryptoService.decrypt(pRequest.getParameter("braceletCardNumber"));
		String classId = mBase64CryptoService.decrypt(pRequest.getParameter("classId"));
		Student stu = new Student(Integer.valueOf(classId), name);
		//TODO update after confirmation
		stu.setFemale(Boolean.valueOf(sexString));
		stu.setBirth(DateUtils.getStudentBirth(birthString));
		stu.setRingNumber(ringNum);
		stu.setRingPhone(ringPhone);
		stu.setAvailable(Boolean.TRUE);
		return stu;
	}

	public Address buildAddress(HttpServletRequest pRequest) throws IOException {
		//TODO update after confirmation
		String fullAddress = mBase64CryptoService.decrypt(pRequest.getParameter("address"));
		if (StringUtils.isBlank(fullAddress)) {
			return null;
		}
		String provinceId = mBase64CryptoService.decrypt(pRequest.getParameter("provId"));
		String cityId = mBase64CryptoService.decrypt(pRequest.getParameter("cityId"));
		Address address = new Address(Integer.valueOf(provinceId), Integer.valueOf(cityId), fullAddress);
		return address;
	}

	public ParentStudentRelationship buildParentStudentRelationship(HttpServletRequest pRequest) throws IOException {
		String phone = mBase64CryptoService.decrypt(pRequest.getParameter("cellPhone"));
		String relationship = mBase64CryptoService.decrypt(pRequest.getParameter("relation"));
		return new ParentStudentRelationship(phone, relationship);
	}

	public Base64CryptoService getBase64CryptoService() {
		return mBase64CryptoService;
	}

	public void setBase64CryptoService(Base64CryptoService pBase64CryptoService) {
		this.mBase64CryptoService = pBase64CryptoService;
	}
}
