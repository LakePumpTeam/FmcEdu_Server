package com.fmc.edu.repository.impl;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IParentRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yove on 5/8/2015.
 */
@Repository(value = "parentRepository")
public class ParentRepository extends BaseRepository implements IParentRepository {

	@Override
	public boolean initialParentProfile(final ParentProfile pParentProfile) {
		return getSqlSession().update(INITIAL_PARENT_PROFILE, pParentProfile) > 0;
	}

	@Override
	public boolean persistParentAddress(final Address pAddress) {
		return getSqlSession().insert(INITIAL_PARENT_ADDRESS, pAddress) > 0;
	}


	@Override
	public ParentProfile queryParentByPhone(final String pParentPhone) {
		return getSqlSession().selectOne(QUERY_PARENT_BY_PHONE, pParentPhone);
	}

	@Override
	public boolean initialParentStudentRelationship(final ParentStudentRelationship pParentStudentRelationship) {
		return getSqlSession().insert(INITIAL_PARENT_STUDENT_RELATIONSHIP, pParentStudentRelationship) > 0;
	}
}
