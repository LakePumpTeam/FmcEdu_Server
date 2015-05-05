package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TempParentProfile;
import com.fmc.edu.repository.ITempProfileRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
			return mTempProfileRepository.updateIdentify(tempParent);
		}
		tempParent = new TempParentProfile(pPhoneNumber, pIdentifyCode);
		return mTempProfileRepository.initialTempProfile(tempParent);
	}

	public ITempProfileRepository getTempProfileRepository() {
		return mTempProfileRepository;
	}

	public void setTempProfileRepository(final ITempProfileRepository pTempProfileRepository) {
		mTempProfileRepository = pTempProfileRepository;
	}
}
