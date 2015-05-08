package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.TempParentProfile;
import com.fmc.edu.repository.ITempParentRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by Yove on 5/4/2015.
 */
@Service(value = "tempParentService")
public class TempParentService {

	@Resource(name = "tempParentRepository")
	private ITempParentRepository mTempParentRepository;

	public boolean registerTempParent(final String pPhoneNumber, final String pIdentifyCode) {
		TempParentProfile tempParent = getTempParentRepository().queryTempParentProfileByPhone(pPhoneNumber);
		if (RepositoryUtils.isItemExist(tempParent)) {
			tempParent.setIdentifyingCode(pIdentifyCode);
			return mTempParentRepository.updateTempParentProfileIdentify(tempParent);
		}
		tempParent = new TempParentProfile(pPhoneNumber, pIdentifyCode);
		return mTempParentRepository.initialTempParentProfile(tempParent);
	}

	public boolean verifyTempParentAuthCode(String pPhoneNumber, String pIdentifyingCode) {
		TempParentProfile tempParent = new TempParentProfile(pPhoneNumber, pIdentifyingCode);
		tempParent = getTempParentRepository().queryTempParentProfileByIdentifyCode(tempParent);
		if (RepositoryUtils.isItemExist(tempParent)) {
			//passed identify
			tempParent.setIdentifyDate(new Timestamp(System.currentTimeMillis()));
			if (getTempParentRepository().updateTempParentProfileIdentify(tempParent)) {
				return getTempParentRepository().initialParentProfile(tempParent.getId());
			}
		}
		return false;
	}


	public ITempParentRepository getTempParentRepository() {
		return mTempParentRepository;
	}

	public void setTempParentRepository(final ITempParentRepository pTempParentRepository) {
		mTempParentRepository = pTempParentRepository;
	}
}
