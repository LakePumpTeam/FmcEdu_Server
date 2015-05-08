package com.fmc.edu.web;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.student.Student;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

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

	public Student buildStudent(HttpServletRequest pRequest) throws IOException {
		String name = decodeInput(pRequest.getParameter("studentname"));
		String sexString = decodeInput(pRequest.getParameter("studentsex"));
		String birthString = decodeInput(pRequest.getParameter("studentbirth"));
		String ringNum = decodeInput(pRequest.getParameter("studentcardnum"));
		String classId = decodeInput(pRequest.getParameter("classid"));
		Student stu = new Student(Integer.valueOf(classId), name);
		//TODO update after confirmation
		stu.setFemale(Boolean.TRUE);
		stu.setBirth(new Date());
		stu.setRingNumber(ringNum);
		stu.setAvailable(Boolean.TRUE);
		return stu;
	}

	public Address buildAddress(HttpServletRequest pRequest) throws IOException {
		//TODO update after confirmation
		String provinceId = decodeInput(pRequest.getParameter(""));
		String cityId = decodeInput(pRequest.getParameter("cityid"));
		String fullAddress = decodeInput(pRequest.getParameter("address"));
		Address address = new Address(Integer.valueOf(provinceId), Integer.valueOf(cityId), fullAddress);
		return address;
	}
}
