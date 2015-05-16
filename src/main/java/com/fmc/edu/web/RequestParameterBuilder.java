package com.fmc.edu.web;

import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
    private static final Logger LOG = Logger.getLogger(RequestParameterBuilder.class);

    @Resource(name = "replacementBase64EncryptService")
    private ReplacementBase64EncryptService mBase64EncryptService;

    public Student buildStudent(HttpServletRequest pRequest) throws IOException, ParseException {
        String name = mBase64EncryptService.decrypt(pRequest.getParameter("studentName"));
        String sexString = mBase64EncryptService.decrypt(pRequest.getParameter("studentSex"));
        String birthString = mBase64EncryptService.decrypt(pRequest.getParameter("studentAge"));
        String ringNum = mBase64EncryptService.decrypt(pRequest.getParameter("braceletNumber"));
        String ringPhone = mBase64EncryptService.decrypt(pRequest.getParameter("braceletCardNumber"));
        String classId = mBase64EncryptService.decrypt(pRequest.getParameter("classId"));
        String studentId = mBase64EncryptService.decrypt(pRequest.getParameter("studentId"));
        Student stu = new Student(Integer.valueOf(classId), name);
        //TODO update after confirmation
        stu.setMale(Boolean.valueOf(sexString));
        stu.setBirth(DateUtils.getStudentBirth(birthString));
        stu.setRingNumber(ringNum);
        stu.setRingPhone(ringPhone);
        stu.setAvailable(Boolean.TRUE);
        if (StringUtils.isNoneBlank(studentId)) {
            stu.setId(Integer.valueOf(studentId));
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name:").append(name)
                .append("\nsexString:").append(sexString)
                .append("\nbirthString:").append(birthString)
                .append("\nringNum:").append(ringNum)
                .append("\nringPhone:").append(ringPhone)
                .append("\nclassId:").append(classId)
                .append("\nstudentId:").append(studentId);
        LOG.debug(stringBuilder.toString());

        return stu;
    }

    public Address buildAddress(HttpServletRequest pRequest) throws IOException {
        //TODO update after confirmation
        String fullAddress = mBase64EncryptService.decrypt(pRequest.getParameter("address"));
        String provinceId = mBase64EncryptService.decrypt(pRequest.getParameter("provId"));
        String cityId = mBase64EncryptService.decrypt(pRequest.getParameter("cityId"));
        String addressId = mBase64EncryptService.decrypt(pRequest.getParameter("addressId"));
        Address address = new Address(Integer.valueOf(provinceId), Integer.valueOf(cityId), fullAddress);
        if (StringUtils.isNoneBlank(addressId)) {
            address.setId(Integer.valueOf(addressId));
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fullAddress:").append(fullAddress)
                .append("\nprovinceId:").append(provinceId)
                .append("\ncityId:").append(cityId)
                .append("\naddressId:").append(addressId);
        LOG.debug(stringBuilder.toString());
        return address;
    }

    public ParentStudentRelationship buildParentStudentRelationship(HttpServletRequest pRequest) throws IOException {
        String phone = mBase64EncryptService.decrypt(pRequest.getParameter("cellPhone"));
        String relationship = mBase64EncryptService.decrypt(pRequest.getParameter("relation"));
        String studentId = mBase64EncryptService.decrypt(pRequest.getParameter("studentId"));
        String parentId = mBase64EncryptService.decrypt(pRequest.getParameter("parentId"));
        ParentStudentRelationship psr = new ParentStudentRelationship(phone, relationship);
        if (StringUtils.isNoneBlank(studentId) && StringUtils.isNoneBlank(parentId)) {
            psr.setParentId(Integer.valueOf(parentId));
            psr.setStudentId(Integer.valueOf(studentId));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone:").append(phone)
                .append("\nrelationship:").append(relationship)
                .append("\nstudentId:").append(studentId)
                .append("\nparentId:").append(parentId);
        LOG.debug(stringBuilder.toString());
        return psr;
    }

    public ParentProfile buildParent(HttpServletRequest pRequest) {
        ParentProfile parent = new ParentProfile();
        String phone = getBase64EncryptService().decrypt(pRequest.getParameter("cellPhone"));
        String parentName = getBase64EncryptService().decrypt(pRequest.getParameter("parentName"));
        String parentId = getBase64EncryptService().decrypt(pRequest.getParameter("parentId"));
        parent.setPhone(phone);
        parent.setName(parentName);
        if (!StringUtils.isBlank(parentId)) {
            parent.setId(Integer.valueOf(parentId));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone:").append(phone)
                .append("\nparentName:").append(parentName)
                .append("\nparentId:").append(parentId);
        LOG.debug(stringBuilder.toString());
        return parent;

    }

    public ReplacementBase64EncryptService getBase64EncryptService() {
        return mBase64EncryptService;
    }

    public void setBase64EncryptService(final ReplacementBase64EncryptService pBase64EncryptService) {
        mBase64EncryptService = pBase64EncryptService;
    }
}
