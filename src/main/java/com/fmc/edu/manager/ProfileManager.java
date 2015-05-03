package com.fmc.edu.manager;

import com.fmc.edu.service.IMessageIdentifyService;
import com.fmc.edu.service.impl.TempProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 5/3/2015.
 */
@Service(value = "profileManager")
public class ProfileManager {

	@Resource(name = "dummyMessageIdentifyService")
	private IMessageIdentifyService mMessageIdentifyService;

	@Resource(name = "tempProfileService")
	private TempProfileService mTempProfileService;

	public String registerParentWithPhoneNum(String pPhoneNumber) {
		String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
		boolean persistentFailure = getTempProfileService().saveTempParent(pPhoneNumber, identifyCode);
		return identifyCode;
	}

	public TempProfileService getTempProfileService() {
		return mTempProfileService;
	}

	public IMessageIdentifyService getMessageIdentifyService() {
		return mMessageIdentifyService;
	}

	public void setMessageIdentifyService(final IMessageIdentifyService pMessageIdentifyService) {
		mMessageIdentifyService = pMessageIdentifyService;
	}

	public void setTempProfileService(final TempProfileService pTempProfileService) {
		mTempProfileService = pTempProfileService;
	}
}
