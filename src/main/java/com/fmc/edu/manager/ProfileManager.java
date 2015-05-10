package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.service.IMessageIdentifyService;
import com.fmc.edu.service.impl.ParentService;
import com.fmc.edu.service.impl.TempParentService;
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

	@Resource(name = "tempParentService")
	private TempParentService mTempParentService;

	@Resource(name = "parentService")
	private ParentService mParentService;


	public String registerTempParent(String pPhoneNumber) {
		String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
		boolean persistentFailure = getTempParentService().registerTempParent(pPhoneNumber, identifyCode);
		return identifyCode;
	}

	public boolean verifyTempParentIdentifyingCode(String pPhoneNumber, final String pPassword, String pIdentifyingCode) {
		return getTempParentService().verifyTempParentAuthCode(pPhoneNumber, pPassword, pIdentifyingCode);
	}

	public boolean registerParent(String pPhoneNumber, int pAddressId) {
		// TODO check the parent is between temp parent and parent
		return getParentService().registerParent(pPhoneNumber, pAddressId);
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

	public TempParentService getTempParentService() {
		return mTempParentService;
	}

	public void setTempParentService(final TempParentService pTempParentService) {
		mTempParentService = pTempParentService;
	}

	public ParentService getParentService() {
		return mParentService;
	}

	public void setParentService(final ParentService pParentService) {
		mParentService = pParentService;
	}
}
