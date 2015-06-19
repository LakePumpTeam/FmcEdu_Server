package com.fmc.edu.web.controller;

import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.ResponseBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yu on 6/20/2015.
 */
@Controller
@RequestMapping("/app")
public class AppInformationController extends BaseController {
    @RequestMapping("/checkVersion")
    @ResponseBody
    public String requestAppVersion(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String deviceType = pRequest.getParameter("deviceType");
        String appVersion = pRequest.getParameter("appVersion");
        if (StringUtils.isBlank(deviceType)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_APP_DEVICE_TYPE_EMPTY);
            return output(responseBean);
        }
        int device = Integer.valueOf(deviceType);
        if (DeviceType.ANDROID == device) {
            responseBean.addData("version", "1.0");
            //TODO add some logic to calculate the result value.
            responseBean.addData("isTheLatestVersion", true);
            //TODO response the latest version app resource url for update
            responseBean.addData("url", "http://182.92.98.174/");
        } else if (DeviceType.IOS == device) {
            responseBean.addData("version", "1.0");
            //TODO add some logic to calculate the result value.
            responseBean.addData("isTheLatestVersion", true);
            //TODO response the latest version app resource url for update
            responseBean.addData("url", "http://182.92.98.174/");
        } else {
            responseBean.addBusinessMsg(ResourceManager.ERROR_APP_UNKNOWN_DEVICE_TYPE, deviceType);
        }
        return output(responseBean);
    }

}
