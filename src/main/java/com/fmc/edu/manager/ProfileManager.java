package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
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
	private IMessageIdentifyService mDummyMessageIdentifyService;

	@Resource(name = "messageIdentifyService")
	private IMessageIdentifyService mMessageIdentifyService;

	@Resource(name = "tempProfileService")
	private TempProfileService mTempProfileService;

	public String registerTempParent(String pPhoneNumber) {
		String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
		boolean persistentFailure = getTempProfileService().registerTempParent(pPhoneNumber, identifyCode);
		return identifyCode;
	}

	public boolean verifyTempParentIdentifyingCode(String pPhoneNumber, String pIdentifyingCode) {
		return getTempProfileService().verifyTempParentAuthCode(pPhoneNumber, pIdentifyingCode);
	}

	/**
	 * Return the MessageIdentifyService according the develop status.
	 *
	 * @return
	 */
	public IMessageIdentifyService getMessageIdentifyService() {
		if (WebConfig.isDevelopment()) {
			return mDummyMessageIdentifyService;
		}
		return mMessageIdentifyService;
	}

	public void setMessageIdentifyService(IMessageIdentifyService pMessageIdentifyService) {
		mMessageIdentifyService = pMessageIdentifyService;
	}

	public IMessageIdentifyService getDummyMessageIdentifyService() {
		return mDummyMessageIdentifyService;
	}

	public void setDummyMessageIdentifyService(IMessageIdentifyService pDummyMessageIdentifyService) {
		mDummyMessageIdentifyService = pDummyMessageIdentifyService;
	}

	public TempProfileService getTempProfileService() {
		return mTempProfileService;
	}

	public void setTempProfileService(final TempProfileService pTempProfileService) {
		mTempProfileService = pTempProfileService;
	}
}
