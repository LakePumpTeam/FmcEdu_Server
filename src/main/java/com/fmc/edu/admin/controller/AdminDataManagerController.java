package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.crypto.impl.PasswordEncryptService;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.relationship.TeacherClassRelationship;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.model.school.School;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.xml.DataHolder;
import com.fmc.edu.xml.DataTemplateParser;
import com.fmc.edu.xml.model.RParent;
import com.fmc.edu.xml.model.RSchool;
import com.fmc.edu.xml.model.RStudent;
import com.fmc.edu.xml.model.RTeacher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/9/8.
 */
@Controller
@RequestMapping("/admin/data")
public class AdminDataManagerController extends AdminTransactionBaseController {

    private static final Logger LOG = Logger.getLogger(AdminDataManagerController.class);

    private static final String DEFAULT_PASSWORD = "123456";

    @Resource(name = "dataTemplateParser")
    private DataTemplateParser mDataTemplateParser;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @Resource(name = "locationManager")
    private LocationManager mLocationManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    @Resource(name = "passwordEncryptService")
    private PasswordEncryptService mPasswordEncryptService;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    private School mSchool;

    @RequestMapping(value = "/import" + GlobalConstant.URL_SUFFIX, method = RequestMethod.POST)
    @ResponseBody
    public String importData(HttpServletRequest pRequest,
                             HttpServletResponse pResponse,
                             @RequestParam(value = "file", required = true) MultipartFile pDataFile) {
        try {
            DataHolder dataHolder = getDataTemplateParser().parser(pDataFile.getInputStream());
            if (dataHolder == null) {
                return "";
            }

            if (!CollectionUtils.isEmpty(dataHolder.getSchools())) {
                consumeSchoolData(dataHolder.getSchools());
            }
            if (mSchool == null) {
                LOG.error("importData(): import data failed, because we can not find school info or can not create new schoo according data file.");
                return "error";
            }
            if (!CollectionUtils.isEmpty(dataHolder.getTeachers())) {
                consumeTeacherData(dataHolder.getTeachers());
            }
            if (!CollectionUtils.isEmpty(dataHolder.getStudents())) {
                consumeStudentData(dataHolder.getStudents());
            }
            if (!CollectionUtils.isEmpty(dataHolder.getParents())) {
                consumeParentData(dataHolder.getParents());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void consumeSchoolData(List<RSchool> pSchools) {
        Pagination pagination = new Pagination(1, 1);
        Iterator<RSchool> schoolIterator = pSchools.iterator();
        RSchool rSchool;
        Map<String, Object> province;
        Map<String, Object> city;
        School school;
        while (schoolIterator.hasNext()) {
            rSchool = schoolIterator.next();
            province = getLocationManager().queryProvincePage(pagination, rSchool.getProvince());
            if (province == null || province.size() == 0
                    || province.get("provinces") == null || CollectionUtils.isEmpty((List) province.get("provinces"))) {
                LOG.warn("consumeSchoolData(): can not find province:" + rSchool.getProvince() + ", ignore this school data:" + rSchool.getSchoolName());
                continue;
            }

            province = (Map<String, Object>) ((List) province.get("provinces")).get(0);
            city = getLocationManager().queryCityPage(pagination, (Integer) province.get("provId"), rSchool.getCity());
            if (city == null || city.size() == 0
                    || city.get("cities") == null || CollectionUtils.isEmpty((List) city.get("cities"))) {
                LOG.warn("consumeSchoolData(): can not find city from province" + rSchool.getProvince() + ", ignore this school data:" + rSchool.getSchoolName());
                continue;
            }

            city = (Map<String, Object>) ((List) city.get("cities")).get(0);
            Map<String, Object> parameter = new HashMap<String, Object>(3);
            parameter.put("schoolName", rSchool.getSchoolName());
            parameter.put("provName", rSchool.getProvince());
            parameter.put("cityName", rSchool.getCity());
            mSchool = getSchoolManager().querySchoolByFields(parameter);
            if (mSchool == null) {
                school = new School();
                school.setCityId((Integer) city.get("cityId"));
                school.setName(rSchool.getSchoolName());
                school.setProvinceId((Integer) province.get("provId"));
                if (getSchoolManager().persistSchool(school)) {
                    mSchool = getSchoolManager().querySchoolByFields(parameter);
                }
            }
        }
    }

    private void consumeTeacherData(List<RTeacher> pTeachers) {
        processTeacherData(pTeachers);
    }

    private void processTeacherData(List<RTeacher> pTeachers) {

        Iterator<RTeacher> teacherIterator = pTeachers.iterator();
        RTeacher rTeacher;
        TeacherProfile teacher;
        BaseProfile teacherProfile;
        while (teacherIterator.hasNext()) {
            TransactionStatus txStatus = ensureTransaction();
            try {
                rTeacher = teacherIterator.next();
                teacherProfile = getMyAccountManager().findUser(rTeacher.getPhone());
                if (teacherProfile != null) {
                    LOG.warn("processTeacherData(): User existed for phone:" + rTeacher.getPhone() + ", ignore this data.");
                    continue;
                }
                teacher = new TeacherProfile();
                teacher.setOnline(false);
                teacher.setName(rTeacher.getName());
                teacher.setBirth(DateUtils.convertStringToDate(rTeacher.getBirthday()));
                teacher.setCourse(rTeacher.getCourse());
                teacher.setBirthStr(rTeacher.getBirthday());
                teacher.setHeadTeacher(rTeacher.isMasterTeacher());
                teacher.setMale(convertSex(rTeacher.getSex()));
                teacher.setResume(rTeacher.getResume());
                teacher.setSchool(mSchool);
                teacher.setCreationDate(DateUtils.getDaysLater(0));
                teacher.setInitialized(true);
                teacher.setAvailable(true);
                teacher.setLastUpdateDate(DateUtils.getDaysLater(0));
                teacher.setPassword(getPasswordEncryptService().encrypt(rTeacher.getPhone() + DEFAULT_PASSWORD));
                teacher.setPhone(rTeacher.getPhone());
                teacher.setSalt(rTeacher.getPhone());
                teacher.setProfileType(ProfileType.TEACHER.getValue());

                int teacherId = getTeacherManager().createTeacherDetail(teacher);
                if (RepositoryUtils.idIsValid(teacherId)) {
                    int classId = saveClassDataByMasterTeacherData(rTeacher, teacherId);
                    if (!RepositoryUtils.idIsValid(classId)) {
                        LOG.warn("processTeacherData(): Can not find class info or can not create class by head teacher data:" + rTeacher.getPhone() + "/" + rTeacher.getName() + ", ignore this data.");
                        txStatus.setRollbackOnly();
                        getTransactionManager().commit(txStatus);
                        continue;
                    }
                    List<TeacherClassRelationship> teacherClassRelationships = getTeacherManager().queryTeacherClassRelationships(teacherId);
                    if (existTeacherClassRelationship(teacherClassRelationships, classId)) {
                        continue;
                    }
                    TeacherClassRelationship relationship = new TeacherClassRelationship();
                    relationship.setAvailable(true);
                    relationship.setClassId(classId);
                    relationship.setHeadTeacher(rTeacher.isMasterTeacher());
                    relationship.setSubTitle("");
                    relationship.setTeacherId(teacherId);
                    if (getTeacherManager().createTeacherClassRelationship(relationship) <= 0) {
                        LOG.error("processTeacherData(): import teacher class relationship data failed.");
                        txStatus.setRollbackOnly();
                    }
                } else {
                    txStatus.setRollbackOnly();
                }
            } catch (ParseException e) {
                LOG.error(e.getMessage());
                txStatus.setRollbackOnly();
            } finally {
                getTransactionManager().commit(txStatus);
            }
        }
    }

    private boolean existTeacherClassRelationship(List<TeacherClassRelationship> pTeacherClassRelationships, int pClsId) {
        if (CollectionUtils.isEmpty(pTeacherClassRelationships)) {
            return false;
        }
        Iterator<TeacherClassRelationship> teacherClassRelationshipIterator = pTeacherClassRelationships.iterator();
        TeacherClassRelationship teacherClassRelationship;
        while (teacherClassRelationshipIterator.hasNext()) {
            teacherClassRelationship = teacherClassRelationshipIterator.next();
            if (teacherClassRelationship.getClassId() == pClsId) {
                return true;
            }
        }
        return false;
    }

    /**
     * get class id by master teacher data, if can not obtain class from db,then persist class by master teacher data.
     *
     * @param pTeacher
     * @return
     */
    private int saveClassDataByMasterTeacherData(RTeacher pTeacher, int pHeadTeacherId) {
        FmcClass fmcClass = new FmcClass();
        int fmcClassId = getExistClassId(pTeacher.getGrade(), pTeacher.getCls());
        if (!RepositoryUtils.idIsValid(fmcClassId) && pTeacher.isMasterTeacher()) {
            fmcClass.setAvailable(true);
            fmcClass.setGrade(pTeacher.getGrade());
            fmcClass.setHeadTeacherId(pHeadTeacherId);
            fmcClass.setRealClass(pTeacher.getCls());
            fmcClass.setHeadTeacherName(pTeacher.getName());
            fmcClass.setSchoolId(mSchool.getId());
            fmcClass.setSchoolName(mSchool.getName());
            fmcClass.setLastUpdateDate(DateUtils.getDaysLater(0));
            fmcClassId = getSchoolManager().createFmcClass(fmcClass);
        }
        return fmcClassId;
    }

    /**
     * check if the grade and class exists in database
     *
     * @return
     */
    private int getExistClassId(String pGrade, String pCls) {
        List<FmcClass> classes = getSchoolManager().queryClassesBySchoolId(mSchool.getId());
        if (CollectionUtils.isEmpty(classes)) {
            return 0;
        }
        Iterator<FmcClass> fmcClassIterator = classes.iterator();
        FmcClass fmcClass;
        while (fmcClassIterator.hasNext()) {
            fmcClass = fmcClassIterator.next();
            if (fmcClass.getGrade().equalsIgnoreCase(pGrade) && fmcClass.getRealClass().equalsIgnoreCase(pCls)) {
                return fmcClass.getId();
            }
        }
        return 0;
    }

    private void consumeStudentData(List<RStudent> pStudents) {
        Iterator<RStudent> studentIterator = pStudents.iterator();
        RStudent rStudent;
        while (studentIterator.hasNext()) {
            rStudent = studentIterator.next();
            if (rStudent == null || StringUtils.isBlank(rStudent.getMasterTeacherName())) {
                LOG.error("consumeStudentData(): No head teacher name.");
                continue;
            }
            Map<String, Object> param = new HashMap<String, Object>(3);
            param.put("grade", rStudent.getGrade());
            param.put("cls", rStudent.getCls());
            param.put("schoolId", mSchool.getId());
            Map<String, Object> headerMaster = getSchoolManager().queryHeadmasterByClassAndSchool(param);
            if (headerMaster == null || headerMaster.size() == 0) {
                LOG.error("consumeStudentData(): Can not find head teacher for school:" + mSchool.getName() + ", grade:" + rStudent.getGrade() + ", class:" + rStudent.getCls() + ", Ignore to import student:" + rStudent.getName());
                continue;
            }
            if (!rStudent.getMasterTeacherName().equalsIgnoreCase((String) headerMaster.get("headTeacherName"))) {
                LOG.error("consumeStudentData(): the class head teacher name is:" + headerMaster.get("headTeacherName") + ", but the student head teacher name is:" + rStudent.getMasterTeacherName() + ", head teacher name is not match, ignore this student:" + rStudent.getName());
                continue;
            }
            Student student = new Student();
            int clsId = getExistClassId(rStudent.getGrade(), rStudent.getCls());
            if (!RepositoryUtils.idIsValid(clsId)) {
                LOG.error("consumeStudentData(): Can not find class. ignore student:" + rStudent.getName());
                continue;
            }
            student.setClassId(clsId);
            student.setAvailable(true);
            student.setBirth(DateUtils.convertStringToDateTime(rStudent.getBirthDay()));
            student.setMale(convertSex(rStudent.getSex()));
            student.setName(rStudent.getName());
            student.setIdentifyCode(rStudent.getStudentSerialNumber());
            if (!getSchoolManager().persistStudent(student)) {
                LOG.error("consumeStudentData(): Saving student failed:" + rStudent.getName());
            }
        }
    }

    private void consumeParentData(List<RParent> pParents) {
        LOG.debug("consumeParentData():****************************START*********************");
        Iterator<RParent> rParentIterator = pParents.iterator();
        RParent rParent;
        while (rParentIterator.hasNext()) {
            TransactionStatus txStatus = ensureTransaction();
            try {
                rParent = rParentIterator.next();
                Student student = getStudentManager().queryStudentByIdentifyCode(rParent.getStudentSerialNumber());
                if (student == null) {
                    LOG.error("consumeParentData(): Can not find student:" + rParent.getStudentName());
                    continue;
                }
                if (!student.getIdentifyCode().equalsIgnoreCase(rParent.getStudentSerialNumber())) {
                    LOG.error("consumeParentData(): Persisted student name:" + student.getName() + " is not equals current student name: " + rParent.getStudentName() + ", ignore parent data:" + rParent.getName());
                    continue;
                }

                ParentProfile parentProfile = new ParentProfile();
                parentProfile.setName(rParent.getName());
                parentProfile.setProfileType(ProfileType.PARENT.getValue());
                parentProfile.setSalt(rParent.getPhone());
                parentProfile.setPassword(getPasswordEncryptService().encrypt(rParent.getPhone() + DEFAULT_PASSWORD));
                parentProfile.setPhone(rParent.getPhone());
                parentProfile.setAvailable(true);
                parentProfile.setOnline(false);
                int profileId = getProfileManager().insertParentProfile(parentProfile);
                if (!RepositoryUtils.idIsValid(profileId)) {
                    LOG.error("consumeParentData(): Saving parent profile failed." + rParent.getName());
                    continue;
                }

                ParentStudentRelationship psr = new ParentStudentRelationship(rParent.getPhone(), rParent.getRelationship());
                psr.setApproved(1);
                psr.setApprovedDate(DateUtils.getDaysLater(0));
                psr.setParentId(profileId);
                psr.setStudentId(student.getId());

                getProfileManager().registerParentStudentRelationship(psr);
            } catch (ProfileException e) {
                txStatus.setRollbackOnly();
                LOG.error("consumeParentData():" + e.getMessage());
            } finally {
                getTransactionManager().commit(txStatus);
            }
        }

        LOG.debug("consumeParentData():****************************END*********************");
    }

    private boolean convertSex(String pSex) {
        return "男".equalsIgnoreCase(pSex) ? true : false;
    }

    public DataTemplateParser getDataTemplateParser() {
        return mDataTemplateParser;
    }

    public void setDataTemplateParser(DataTemplateParser pDataTemplateParser) {
        mDataTemplateParser = pDataTemplateParser;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }

    public LocationManager getLocationManager() {
        return mLocationManager;
    }

    public void setLocationManager(LocationManager pLocationManager) {
        mLocationManager = pLocationManager;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(TeacherManager pTeacherManager) {
        mTeacherManager = pTeacherManager;
    }

    public PasswordEncryptService getPasswordEncryptService() {
        return mPasswordEncryptService;
    }

    public void setPasswordEncryptService(PasswordEncryptService pPasswordEncryptService) {
        mPasswordEncryptService = pPasswordEncryptService;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }
}
