package com.fmc.edu.web;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "requestParameterBuilder")
public class RequestParameterBuilder {

	protected String decodeInput(final String pParameter) throws IOException {
		if (StringUtils.isBlank(pParameter)) {
			return null;
		}
		return new String((new BASE64Decoder()).decodeBuffer(pParameter), Charset.forName(GlobalConstant.CHARSET_UTF8));
	}

	public Student buildStudent(HttpServletRequest pRequest) throws IOException, ParseException {
		String name = decodeInput(pRequest.getParameter("studentName"));
		String sexString = decodeInput(pRequest.getParameter("studentSex"));
		String birthString = decodeInput(pRequest.getParameter("studentAge"));
		String ringNum = decodeInput(pRequest.getParameter("braceletNumber"));
		String ringPhone = decodeInput(pRequest.getParameter("braceletCardNumber"));
		String classId = decodeInput(pRequest.getParameter("classId"));
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
		String fullAddress = decodeInput(pRequest.getParameter("address"));
		if (StringUtils.isBlank(fullAddress)) {
			return null;
		}
		String provinceId = decodeInput(pRequest.getParameter("provId"));
		String cityId = decodeInput(pRequest.getParameter("cityId"));
		Address address = new Address(Integer.valueOf(provinceId), Integer.valueOf(cityId), fullAddress);
		return address;
	}

	public ParentStudentRelationship buildParentStudentRelationship(HttpServletRequest pRequest) throws IOException {
		String phone = decodeInput(pRequest.getParameter("cellPhone"));
		String relationship = decodeInput(pRequest.getParameter("relation"));
		return new ParentStudentRelationship(phone, relationship);
	}

}
