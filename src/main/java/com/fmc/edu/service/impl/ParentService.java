package com.fmc.edu.service.impl;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.IParentRepository;
import com.fmc.edu.util.RepositoryUtils;
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

    public boolean updateParentProfile(final String pPhoneNumber, final ParentProfile pParent) {
        //parent.setFreeTrialEndDate(DateUtils.getDaysLater(30));
        return getParentRepository().updateParentProfile(pParent);
    }

    public boolean insertParentProfile(final ParentProfile pParent) {
        return getParentRepository().insertParentProfile(pParent);
    }

    public boolean registerParentAddress(String pPhoneNumber, Address pAddress) {
        if (pAddress.getId() == 0) {
            Address address = getParentRepository().queryAddressByPhone(pPhoneNumber);
            if (!RepositoryUtils.isItemExist(address)) {
                // create new address record
                pAddress.setCreationDate(new Timestamp(System.currentTimeMillis()));
                return getParentRepository().persistParentAddress(pAddress);
            }
            pAddress.setId(address.getId());
        }
        // update address
        return getParentRepository().updateParentAddress(pAddress);
    }

    public boolean registerParentStudentRelationship(final ParentStudentRelationship pParentStudentRelationship) {
        ParentProfile parent = null;
        if (pParentStudentRelationship.getParentId() == 0) {
            parent = getParentRepository().queryParentByPhone(pParentStudentRelationship.getParentPhone());
        } else {
            parent = getParentRepository().queryParentById(pParentStudentRelationship.getParentId());
        }
        if (parent != null) {
            pParentStudentRelationship.setParentId(parent.getId());
            pParentStudentRelationship.setCreationDate(new Timestamp(System.currentTimeMillis()));
            return getParentRepository().initialParentStudentRelationship(pParentStudentRelationship);
        }
        return false;
    }

    public boolean updateProfileName(BaseProfile pProfile) {
        return getParentRepository().updateProfileName(pProfile);
    }

    public boolean initialProfile(BaseProfile baseProfile) {
        return getParentRepository().initialProfile(baseProfile);
    }

    public ParentProfile queryParentByPhone(String pParentPhone) {
        return getParentRepository().queryParentByPhone(pParentPhone);
    }

    public IParentRepository getParentRepository() {
        return mParentRepository;
    }

    public void setParentRepository(final IParentRepository pParentRepository) {
        mParentRepository = pParentRepository;
    }
}
