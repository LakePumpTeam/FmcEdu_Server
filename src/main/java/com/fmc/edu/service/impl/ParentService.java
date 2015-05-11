package com.fmc.edu.service.impl;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.IParentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

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
		//		parent.setFreeTrialEndDate(DateUtils.getDaysLater(30));
		return getParentRepository().initialParentProfile(parent);
	}

	public boolean registerParentAddress(String pPhoneNumber, Address pAddress) {
		pAddress.setCreationDate(new Timestamp(System.currentTimeMillis()));
		return getParentRepository().persistParentAddress(pAddress);
	}

	public boolean registerParentStudentRelationship(final ParentStudentRelationship pParentStudentRelationship) {
		ParentProfile parent = getParentRepository().queryParentByPhone(pParentStudentRelationship.getParentPhone());
		if (parent != null) {
			pParentStudentRelationship.setParentId(parent.getId());
			pParentStudentRelationship.setCreationDate(new Timestamp(System.currentTimeMillis()));
			return getParentRepository().initialParentStudentRelationship(pParentStudentRelationship);
		}
		return false;
	}

	public IParentRepository getParentRepository() {
		return mParentRepository;
	}

	public void setParentRepository(final IParentRepository pParentRepository) {
		mParentRepository = pParentRepository;
	}
}
