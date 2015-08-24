package com.fmc.edu.web;

import com.fmc.edu.exception.InvalidIdException;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.service.impl.ParentService;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.ValidationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "requestParameterBuilder")
public class RequestParameterBuilder {
    private static final Logger LOG = Logger.getLogger(RequestParameterBuilder.class);

    @Resource(name = "parentService")
    private ParentService mParentService;

    public Student buildStudent(HttpServletRequest pRequest) throws IOException, ParseException {
        String name = pRequest.getParameter("studentName");
        String sexString = pRequest.getParameter("studentSex");
        String birthString = pRequest.getParameter("studentAge");
        String ringNum = pRequest.getParameter("braceletNumber");
        String ringPhone = pRequest.getParameter("braceletCardNumber");
        String classId = pRequest.getParameter("classId");
        String studentId = pRequest.getParameter("studentId");
        Student stu = new Student(Integer.valueOf(classId), name);
        //TODO update after confirmation
        stu.setMale(Boolean.valueOf(sexString));
        stu.setBirth(DateUtils.convertStringToDate(birthString));
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

    public Address buildAddress(HttpServletRequest pRequest) throws IOException, InvalidIdException {
        //TODO update after confirmation
        String fullAddress = pRequest.getParameter("address");
        String provinceId = pRequest.getParameter("provId");
        if (!RepositoryUtils.idIsValid(provinceId)) {
            throw new InvalidIdException(provinceId);
        }
        String cityId = pRequest.getParameter("cityId");
        if (!RepositoryUtils.idIsValid(cityId)) {
            throw new InvalidIdException(cityId);
        }
        String addressId = pRequest.getParameter("addressId");
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
        String phone = pRequest.getParameter("cellPhone");
        String relationship = pRequest.getParameter("relation");
        String studentId = pRequest.getParameter("studentId");
        String parentId = pRequest.getParameter("parentId");
        boolean isAudit = Boolean.valueOf(pRequest.getParameter("isAudit"));
        List<ParentStudentRelationship> psrs = null;
        if (StringUtils.isNoneBlank(studentId) && StringUtils.isNoneBlank(parentId)) {
            Map<String, Object> queryParam = new HashMap<String, Object>(2);
            queryParam.put("parentId", parentId);
            queryParam.put("studentId", studentId);
            psrs = getmParentService().queryParentStudentRelationship(queryParam);
        }
        ParentStudentRelationship psr;
        if (!CollectionUtils.isEmpty(psrs)) {
            psr = psrs.get(0);
            if (isAudit) {
                //Need to verify again
                psr.setApproved(0);
            }
            psr.setRelationship(relationship);
        } else {
            psr = new ParentStudentRelationship(phone, relationship);
        }
       /* if (StringUtils.isNoneBlank(studentId) && StringUtils.isNoneBlank(parentId)) {
            psr.setParentId(Integer.valueOf(parentId));
            psr.setStudentId(Integer.valueOf(studentId));
        }*/
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone:").append(phone)
                .append("\nrelationship:").append(relationship)
                .append("\nstudentId:").append(studentId)
                .append("\nparentId:").append(parentId);
        LOG.debug(stringBuilder.toString());
        return psr;
    }

    public ParentProfile buildParent(HttpServletRequest pRequest, final MyAccountManager pMyAccountManager) throws ProfileException {
        ParentProfile parent = new ParentProfile();
        String phone = pRequest.getParameter("cellPhone");
        if (phone == null || !ValidationUtils.isValidPhoneNumber(phone)) {
            throw new ProfileException(ResourceManager.VALIDATION_USER_PHONE_ERROR, phone);
        }
        String parentName = pRequest.getParameter("parentName");
        String parentId = pRequest.getParameter("parentId");
        parent.setPhone(phone);
        parent.setName(parentName);
        if (!RepositoryUtils.idIsValid(parentId)) {
            BaseProfile baseProfile = pMyAccountManager.findUser(phone);
            if (baseProfile == null) {
                throw new ProfileException(ResourceManager.ERROR_NOT_FIND_USER, phone);
            }
            parent.setId(baseProfile.getId());
        } else {
            parent.setId(Integer.valueOf(parentId));
        }
        if (!RepositoryUtils.idIsValid(parent.getId())) {
            throw new ProfileException(ResourceManager.ERROR_NOT_FIND_USER, phone);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone:").append(phone)
                .append("\nparentName:").append(parentName)
                .append("\nparentId:").append(parentId);
        LOG.debug(stringBuilder.toString());
        return parent;

    }


    public TeacherProfile buildTeacher(final HttpServletRequest pRequest) throws ParseException {
        TeacherProfile teacher = new TeacherProfile();
        int id = Integer.valueOf(pRequest.getParameter("teacherId"));
        String name = pRequest.getParameter("teacherName");
        boolean male = Boolean.valueOf(pRequest.getParameter("teacherSex"));
        String phone = pRequest.getParameter("cellPhone");
        Date birth = DateUtils.convertStringToDate(pRequest.getParameter("teacherBirth"));
        String resume = pRequest.getParameter("resume");
        String course = pRequest.getParameter("course");
        teacher.setId(id);
        teacher.setName(name);
        teacher.setPhone(phone);
        teacher.setBirth(birth);
        teacher.setResume(resume);
        teacher.setCourse(course);
        teacher.setMale(male);
        return teacher;
    }

    public ParentService getmParentService() {
        return mParentService;
    }

    public void setmParentService(ParentService mParentService) {
        this.mParentService = mParentService;
    }
}
