package com.fmc.edu.service.impl;

import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.IParentRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "parentService")
public class ParentService {

    @Resource(name = "parentRepository")
    private IParentRepository mParentRepository;

    public boolean updateParentProfile(final ParentProfile pParent) {
        //parent.setFreeTrialEndDate(DateUtils.getDaysLater(30));
        return getParentRepository().updateParentProfile(pParent);
    }

    public int insertParentProfile(final ParentProfile pParent) {
        return getParentRepository().insertParentProfile(pParent);
    }

    public boolean registerParentAddress(int profileId, Address pAddress) {
        if (pAddress.getId() == 0) {
            Address address = getParentRepository().queryAddressByProfileId(profileId);
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

    public ParentStudentRelationship queryParentStudentRelationship(final int parentId, final int studentId) {
        return getParentRepository().queryParentStudentRelationship(parentId, studentId);
    }

    public boolean registerParentStudentRelationship(final ParentStudentRelationship pParentStudentRelationship) throws ProfileException {
        ParentProfile parent = null;
        if (pParentStudentRelationship.getParentId() == 0) {
            parent = getParentRepository().queryParentByPhone(pParentStudentRelationship.getParentPhone());
            if (parent == null) {
                throw new ProfileException(ResourceManager.ERROR_NOT_FIND_USER, pParentStudentRelationship.getParentPhone());
            }
            pParentStudentRelationship.setParentId(parent.getId());
        }
        if (queryParentStudentRelationship(pParentStudentRelationship.getParentId(), pParentStudentRelationship.getStudentId()) != null) {
            pParentStudentRelationship.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
            return updateParentStudentRelationship(pParentStudentRelationship);
        } else {
            pParentStudentRelationship.setCreationDate(new Timestamp(System.currentTimeMillis()));
            return getParentRepository().initialParentStudentRelationship(pParentStudentRelationship);
        }
    }

    public boolean updateParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship) {
        return getParentRepository().updateParentStudentRelationship(pParentStudentRelationship);
    }

    public List<ParentStudentRelationship> queryParentStudentRelationship(Map<String, Object> pParentStudentRelationship) {
        return getParentRepository().queryParentStudentRelationship(pParentStudentRelationship);
    }

    public int initialProfile(BaseProfile baseProfile) {
        return getParentRepository().initialProfile(baseProfile);
    }

    public ParentProfile queryParentByPhone(String pParentPhone) {
        return getParentRepository().queryParentByPhone(pParentPhone);
    }

    public ParentProfile queryParentById(int pParentId) {
        return getParentRepository().queryParentById(pParentId);
    }

    public IParentRepository getParentRepository() {
        return mParentRepository;
    }

    public void setParentRepository(final IParentRepository pParentRepository) {
        mParentRepository = pParentRepository;
    }
}
