package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
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

	@Resource(name = "schoolManager")
	private SchoolManager mSchoolManager;


	public String registerTempParent(String pPhoneNumber) {
		String identifyCode = getMessageIdentifyService().sendIdentifyRequest(pPhoneNumber);
		boolean persistentFailure = getTempParentService().registerTempParent(pPhoneNumber, identifyCode);
		return identifyCode;
	}

	public boolean verifyTempParentIdentifyingCode(String pPhoneNumber, final String pPassword, String pIdentifyingCode) {
		return getTempParentService().verifyTempParentAuthCode(pPhoneNumber, pPassword, pIdentifyingCode);
	}

	public boolean registerParentAddress(String pPhoneNumber, Address pAddress) {
		// TODO check the parent is between temp parent and parent
		return getParentService().registerParentAddress(pPhoneNumber, pAddress);
	}


	public void registerRelationshipBetweenStudent(final ParentStudentRelationship pParentStudentRelationship, final Student pStudent, final int pAddressId) {
		boolean persist = getSchoolManager().persistStudent(pStudent);
		if (persist && pStudent.getId() > 0) {
			pParentStudentRelationship.setStudentId(pStudent.getId());
			boolean register = getParentService().registerParentStudentRelationship(pParentStudentRelationship);
			if (register) {
				getParentService().registerParent(pParentStudentRelationship.getParentPhone(), pAddressId);
			}
		}
	}

	public ParentProfile queryParentByPhone(String pParentPhone) {
		return getParentService().queryParentByPhone(pParentPhone);
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

	public SchoolManager getSchoolManager() {
		return mSchoolManager;
	}

	public void setSchoolManager(final SchoolManager pSchoolManager) {
		mSchoolManager = pSchoolManager;
	}
}
