package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.app.AppSetting;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.ResponseBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yu on 6/20/2015.
 */
@Controller
@RequestMapping("/app")
public class AppInformationController extends BaseController {
    private static final Logger LOG = Logger.getLogger(AppInformationController.class);

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

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

    @RequestMapping(value = "/appSetting" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String appSetting(HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();

        try {
            String userId = pRequest.getParameter("userId");
            String isBel = pRequest.getParameter("isBel");
            String isVibra = pRequest.getParameter("isVibra");
            if (com.fmc.edu.util.StringUtils.isBlank(isBel) && com.fmc.edu.util.StringUtils.isBlank(isVibra)) {
                return output(responseBean);
            }

            BaseProfile user = getMyAccountManager().findUserById(userId);
            if (user == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userId);
                return output(responseBean);
            }

            AppSetting appSetting = getMyAccountManager().queryAppSetting(user.getId());
            if (appSetting == null) {
                appSetting = new AppSetting();
                appSetting.setProfileId(user.getId());
            }
            appSetting.setIsBel(Boolean.valueOf(isBel));
            appSetting.setIsVibra(Boolean.valueOf(isVibra));
            getMyAccountManager().insertOrUpdateAppSetting(appSetting);
        } catch (Exception ex) {
            txStatus.setRollbackOnly();
            LOG.error(ex.getMessage());
            responseBean.addErrorMsg(ex);
        } finally {
            getTransactionManager().commit(txStatus);
            return output(responseBean);
        }
    }

    @RequestMapping(value = "/getAppSetting" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String getAppSetting(HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String userId = pRequest.getParameter("userId");
        BaseProfile user = getMyAccountManager().findUserById(userId);
        if (user == null) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userId);
            return output(responseBean);
        }

        AppSetting appSetting = getMyAccountManager().queryAppSetting(user.getId());
        if (appSetting == null) {
            appSetting = new AppSetting();
            appSetting.setIsVibra(true);
            appSetting.setIsBel(true);
            appSetting.setProfileId(user.getId());
        }
        responseBean.addData("isVibra", appSetting.isIsVibra());
        responseBean.addData("isBel", appSetting.isIsBel());
        responseBean.addData("profileId", appSetting.getProfileId());
        return output(responseBean);
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }
}
