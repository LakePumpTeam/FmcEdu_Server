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

	@Resource(name = "webConfig")
	private WebConfig mWebConfig;

	public String registerParentWithPhoneNum(String pPhoneNumber) {
		String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
		boolean persistentFailure = getTempProfileService().saveTempParent(pPhoneNumber, identifyCode);
		return identifyCode;
	}

	/**
	 * Return the MessageIdentifyService according the develop status.
	 *
	 * @return
	 */
	public IMessageIdentifyService getMessageIdentifyService() {
		if (mWebConfig.isDevelopment()) {
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


	public WebConfig getWebConfig() {
		return mWebConfig;
	}

	public void setWebConfig(WebConfig pWebConfig) {
		mWebConfig = pWebConfig;
	}

	public TempProfileService getTempProfileService() {
		return mTempProfileService;
	}

	public void setTempProfileService(final TempProfileService pTempProfileService) {
		mTempProfileService = pTempProfileService;
	}
}
