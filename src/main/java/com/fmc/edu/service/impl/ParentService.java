package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.repository.IParentRepository;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "parentService")
public class ParentService {

	@Resource(name = "parentRepository")
	private IParentRepository mParentRepository;

	public boolean registerParent(final String pPhoneNumber, final int pAddressId) {
		ParentProfile parent = new ParentProfile();
		parent.setPhone(pPhoneNumber);
		parent.setAddressId(pAddressId);
		parent.setFreeTrialEndDate(DateUtils.getDaysLater(30));
		return getParentRepository().initialParentProfile(parent);
	}

	public IParentRepository getParentRepository() {
		return mParentRepository;
	}

	public void setParentRepository(final IParentRepository pParentRepository) {
		mParentRepository = pParentRepository;
	}
}
