package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 5/22/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminNavigationController {

	@RequestMapping(value = "/home" + GlobalConstant.URL_SUFFIX)
	public String toHome(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		return "admin/index";
	}

	@RequestMapping(value = "/login" + GlobalConstant.URL_SUFFIX)
	public String toLogin(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		return "admin/login";
	}


}
