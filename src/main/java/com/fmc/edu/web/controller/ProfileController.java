package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.ProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yove on 5/4/2015.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

	private static final String ERROR_EMPTY_PHONE = "Sorry, the phone should not be empty.";

	@Resource(name = "profileManager")
	private ProfileManager mProfileManager;

	@RequestMapping(value = ("/requestPhoneIdentify" + GlobalConstant.URL_SUFFIX))
	@ResponseBody
	public String requestPhoneIdentify(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellphone) throws IOException {
		String phone = decodeInput(cellphone);
		// output error if phone number is blank
		if (StringUtils.isBlank(phone)) {
			return generateJsonOutput(Boolean.FALSE, null, ERROR_EMPTY_PHONE);
		}
		String identifyCode = getProfileManager().registerTempParentByPhoneNum(phone);
		// request identify failed if identify is blank
		// TODO should not return code, return for test
		return generateJsonOutput(StringUtils.isNotBlank(identifyCode), identifyCode, null);
	}

	public ProfileManager getProfileManager() {
		return mProfileManager;
	}

	public void setProfileManager(final ProfileManager pProfileManager) {
		mProfileManager = pProfileManager;
	}
}
