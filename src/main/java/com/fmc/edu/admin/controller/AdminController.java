package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.RoleConstant;
import com.fmc.edu.crypto.impl.PasswordEncryptService;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.profile.BaseProfile;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class AdminController extends AdminTransactionBaseController {

	private static final Logger LOG = Logger.getLogger(AdminController.class);

	private static final String REDIRECT_LOGIN = "redirect:/admin/login";

	@Resource(name = "myAccountManager")
	private MyAccountManager mMyAccountManager;

	@Resource(name = "passwordEncryptService")
	private PasswordEncryptService mPasswordEncryptService;

	@RequestMapping(value = "/login" + GlobalConstant.URL_SUFFIX)
	public String requestLogin(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null && currentUser.getPrincipal() != null) {
			String userName = currentUser.getPrincipal().toString();
			if (currentUser.isAuthenticated()) {
				LOG.debug(String.format("User: %s has already logged in.", userName));
				return REDIRECT_HOME;
			}
			if (currentUser.isRemembered()) {
				// auto login for valid cookie
				LOG.debug(String.format("User: %s is a recognized user, try to build token to auto login.", userName));
				try {
					BaseProfile baseProfile = getMyAccountManager().findUser(userName);
					if (baseProfile == null) {
						LOG.warn(String.format("Cannot find profile with name: %s", userName));
						return REDIRECT_LOGIN;
					}
					UsernamePasswordToken authenticationToken = new UsernamePasswordToken(userName, baseProfile.getPassword());
					authenticationToken.setRememberMe(true);
					currentUser.login(authenticationToken);
					currentUser.checkRole(RoleConstant.ADMIN_ROLE);
				} catch (Exception e) {
					LOG.error(String.format("User: %s auto-login fail.", userName), e);
					return REDIRECT_LOGIN;
				}
				LOG.debug(String.format("User: %s auto-login success.", userName));
				return REDIRECT_HOME;
			}
		}
		return "admin/login";
	}

	@RequestMapping(value = "/authorize" + GlobalConstant.URL_SUFFIX, method = RequestMethod.POST)
	public String login(HttpServletRequest pRequest, HttpServletResponse pResponse,
			Model pModel,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null && currentUser.isAuthenticated()) {
			LOG.debug(String.format("User: %s has already logged in.", userName));
			return REDIRECT_HOME;
		}
		try {
			BaseProfile baseProfile = getMyAccountManager().findUser(userName);
			if (baseProfile == null) {
				String errorMsg = String.format("Cannot find profile with name: %s", userName);
				String frontErrorMsg = getResourceManager().getMessage(pRequest, ResourceManager.ERROR_NOT_FIND_USER, new Object[] {userName}, errorMsg);
				addErrorMessage(pModel, frontErrorMsg);
				LOG.warn(errorMsg);
				return REDIRECT_LOGIN;
			}
			String decodePassword = getPasswordEncryptService().encrypt(baseProfile.getSalt() + password);
			UsernamePasswordToken authenticationToken = new UsernamePasswordToken(userName, decodePassword);
			authenticationToken.setRememberMe(true);
			currentUser.login(authenticationToken);
			currentUser.checkRole(RoleConstant.ADMIN_ROLE);
		} catch (UnknownAccountException e) {
			LOG.error(e.getMessage(), e);
			return REDIRECT_LOGIN + "?autho=unknownAccount";
		} catch (IncorrectCredentialsException e) {
			LOG.error(e.getMessage(), e);
			return REDIRECT_LOGIN + "?autho=pwd";
		} catch (LockedAccountException e) {
			LOG.error(e.getMessage(), e);
			return REDIRECT_LOGIN + "?autho=accountLock";
		} catch (ExcessiveAttemptsException e) {
			LOG.error(e.getMessage(), e);
			return REDIRECT_LOGIN + "?autho=attempt";
		} catch (AuthenticationException e) {
			LOG.error(e.getMessage(), e);
			return REDIRECT_LOGIN + "?autho=authoFailed";
		}
		LOG.debug(String.format("User: %s login success.", userName));
		return REDIRECT_HOME;
	}

	@RequestMapping(value = "/logout" + GlobalConstant.URL_SUFFIX)
	public String logout(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser == null) {
			return REDIRECT_LOGIN;
		}
		currentUser.logout();
		return REDIRECT_LOGIN;
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
