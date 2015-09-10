package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.crypto.impl.PasswordEncryptService;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.web.controller.BaseController;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yu on 5/28/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final Logger LOG = Logger.getLogger(AdminController.class);

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "passwordEncryptService")
    private PasswordEncryptService mPasswordEncryptService;

    @RequestMapping(value = "/authorize" + GlobalConstant.URL_SUFFIX, method = RequestMethod.POST)
    public String login(HttpServletRequest pRequest,
                        HttpServletResponse pResponse,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "password", required = true) String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser == null) {
            return "redirect:/admin/home";
        }
        try {
            BaseProfile baseProfile = getMyAccountManager().findUser(userName);
            if (baseProfile == null) {
                return "redirect:/admin/login";
            }
            String decodePassword = getPasswordEncryptService().encrypt(baseProfile.getSalt() + password);
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken(userName, decodePassword);
            authenticationToken.setRememberMe(true);
            currentUser.login(authenticationToken);

            currentUser.checkRole("admin");
        } catch (UnknownAccountException e) {
            LOG.error(e);
            return "redirect:/admin/login?autho=unknownAccount";
        } catch (IncorrectCredentialsException e) {
            LOG.error(e);
            return "redirect:/admin/login?autho=pwd";
        } catch (LockedAccountException e) {
            LOG.error(e);
            return "redirect:/admin/login?autho=accountLock";
        } catch (ExcessiveAttemptsException e) {
            LOG.error(e);
            return "redirect:/admin/login?autho=attempt";
        } catch (AuthenticationException e) {
            LOG.error(e);
            return "redirect:/admin/login?autho=authoFailed";
        }
        return "redirect:/admin/home";
    }

    @RequestMapping(value = "/logout" + GlobalConstant.URL_SUFFIX)
    public String logout(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser == null) {
            return "redirect:/admin/login";
        }
        currentUser.logout();
        return "redirect:/admin/login";
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public PasswordEncryptService getPasswordEncryptService() {
        return mPasswordEncryptService;
    }

    public void setPasswordEncryptService(PasswordEncryptService pPasswordEncryptService) {
        mPasswordEncryptService = pPasswordEncryptService;
    }
}
