package com.fmc.edu.web.controller;

import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/5/10.
 */
@Controller
@RequestMapping("/school")
public class SchoolController extends BaseController{
    private static final Logger LOG = Logger.getLogger(SchoolController.class);

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @RequestMapping("/requestSchools")
    @ResponseBody
    public String requestSchools(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cityId, final String filterKey){
        String responseMsg;
        try {
            String  city = decodeInput(cityId);
            String  filter = decodeInput(filterKey);
            Pagination pagination = buildPagination(pRequest);

            Map<String,Object> schools = getSchoolManager().querySchoolsPage(pagination, Integer.valueOf(city), filter);
            responseMsg = generateJsonOutput(Boolean.TRUE, schools, null);
        } catch (IOException e) {
             responseMsg = generateJsonOutput(Boolean.FALSE, null, e.getMessage());
             LOG.error(e);
        }
        return responseMsg;
    }

    @RequestMapping("/requestClasses")
    @ResponseBody
    public String requestClasses(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String schoolId, final String filterKey){
        String responseMsg;
        try {
            String  school = decodeInput(schoolId);
            String  filter = decodeInput(filterKey);
            Pagination pagination = buildPagination(pRequest);

            Map<String,Object> schools = getSchoolManager().queryClassesPage(pagination, Integer.valueOf(school), filter);
            responseMsg = generateJsonOutput(Boolean.TRUE, schools, null);
        } catch (IOException e) {
            responseMsg = generateJsonOutput(Boolean.FALSE, null, e.getMessage());
            LOG.error(e);
        }
        return responseMsg;
    }


    @RequestMapping("/requestHeadTeacher")
    @ResponseBody
    public String requestHeadmaster(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String schoolId, final String classId){
        String responseMsg;
        try {
            String  school = decodeInput(schoolId);
            String  correspondingClass = decodeInput(classId);

            List<Map<String, String>> schools = getSchoolManager().queryHeadmasterPage(Integer.valueOf(school), Integer.valueOf(correspondingClass));
            responseMsg = generateJsonOutput(Boolean.TRUE, schools, null);
        } catch (IOException e) {
            responseMsg = generateJsonOutput(Boolean.FALSE, null, e.getMessage());
        }
        return responseMsg;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        this.mSchoolManager = pSchoolManager;
    }
}
