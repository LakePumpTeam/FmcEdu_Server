package com.fmc.edu.web.controller;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.HomePageManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.web.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Yu on 2015/5/16.
 */
@Controller
@RequestMapping("/home")
public class HomePageController extends BaseController {
    private static final Logger LOG = Logger.getLogger(HomePageController.class);

    @Resource(name = "homePageManager")
    private HomePageManager mHomePageManager;

    @RequestMapping("/requestHeaderTeacherForHomePage")
    @ResponseBody
    public String requestHeaderTeacherForHomePage(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            preRequestHeaderTeacherForHomePage(pRequest, responseBean);
            if (!responseBean.isSuccess()) {
                return responseBean.toString();
            }
            String profileId = pRequest.getParameter("userId");
            String optionId = pRequest.getParameter("optionId");
            Map<String, Object> homePageTeacher = getHomePageManager().obtainHeaderTeacher(profileId, optionId);
            responseBean.addData(homePageTeacher);
        } catch (ProfileException e) {
            responseBean.addBusinessMsg(e.getMessage(), e.getArgs());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            responseBean.addErrorMsg(e);
        }
        return responseBean.toString();
    }

    private void preRequestHeaderTeacherForHomePage(final HttpServletRequest pRequest, ResponseBean pResponseBean) {
        String profileId = pRequest.getParameter("userId");
        if (StringUtils.isBlank(profileId)) {
            pResponseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_EMPTY);
            return;
        }
    }

    public HomePageManager getHomePageManager() {
        return mHomePageManager;
    }

    public void setHomePageManager(HomePageManager pHomePageManager) {
        this.mHomePageManager = pHomePageManager;
    }

}
