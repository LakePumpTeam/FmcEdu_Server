package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TempParentProfile;
import com.fmc.edu.repository.ITempProfileRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by Yove on 5/4/2015.
 */
@Service(value = "tempProfileService")
public class TempProfileService {

	@Resource(name = "tempProfileRepository")
	private ITempProfileRepository mTempProfileRepository;

	public boolean registerTempParent(final String pPhoneNumber, final String pIdentifyCode) {
		TempParentProfile tempParent = getTempProfileRepository().queryTempParentProfileByPhone(pPhoneNumber);
		if (RepositoryUtils.isItemExist(tempParent)) {
			tempParent.setIdentifyingCode(pIdentifyCode);
			return mTempProfileRepository.updateTempParentProfileIdentify(tempParent);
		}
		tempParent = new TempParentProfile(pPhoneNumber, pIdentifyCode);
		return mTempProfileRepository.initialTempParentProfile(tempParent);
	}

	public boolean verifyTempParentAuthCode(String pPhoneNumber, String pIdentifyingCode) {
		TempParentProfile tempParent = new TempParentProfile(pPhoneNumber, pIdentifyingCode);
		tempParent = getTempProfileRepository().queryTempParentProfileByIdentifyCode(tempParent);
		if (RepositoryUtils.isItemExist(tempParent)) {
			//passed identify
			tempParent.setIdentifyDate(new Timestamp(System.currentTimeMillis()));
			if (getTempProfileRepository().updateTempParentProfileIdentify(tempParent)) {
				return getTempProfileRepository().initialParentProfile(tempParent.getId());
			}
		}
		return false;
	}

	public ITempProfileRepository getTempProfileRepository() {
		return mTempProfileRepository;
	}

	public void setTempProfileRepository(final ITempProfileRepository pTempProfileRepository) {
		mTempProfileRepository = pTempProfileRepository;
	}
}
