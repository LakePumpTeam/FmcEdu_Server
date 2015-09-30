package com.fmc.edu.repository.impl;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IParentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
@Repository(value = "parentRepository")
public class ParentRepository extends BaseRepository implements IParentRepository {

	@Override
	public boolean updateParentProfile(final ParentProfile pParentProfile) {
		return getSqlSession().update(INITIAL_PARENT_PROFILE, pParentProfile) > 0;
	}

	@Override
	public int insertParentProfile(ParentProfile pParentProfile) {
		if (getSqlSession().insert(INSERT_PARENT_PROFILE, pParentProfile) > 0) {
			return pParentProfile.getId();
		}
		return 0;
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

	@Override
	public boolean updateParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship) {
		return getSqlSession().update(UPATE_PARENT_STUDENT_RELATIONSHIP, pParentStudentRelationship) > 0;
	}

	@Override
	public List<ParentStudentRelationship> queryParentStudentRelationship(Map<String, Object> pParentStudentRelationship) {
		return getSqlSession().selectList(QUERY_PARENT_STUDENT_RELATIONSHIP, pParentStudentRelationship);
	}

	@Override
	public ParentStudentRelationship queryParentStudentRelationship(int parentId, int studentId) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("parentId", parentId);
		params.put("studentId", studentId);
		List<ParentStudentRelationship> parentStudentRelationships = getSqlSession().selectList(QUERY_PARENTSTUDENTRELATION_BY_PARENTID_STUDENTID, params);
		if (CollectionUtils.isEmpty(parentStudentRelationships)) {
			return null;
		}
		return parentStudentRelationships.get(0);
	}

	@Override
	public Address queryAddressByProfileId(final int PprofileId) {
		return getSqlSession().selectOne(QUERY_ADDRESS_BY_PROFILE_ID, PprofileId);
	}

	@Override
	public boolean updateParentAddress(final Address pAddress) {
		return getSqlSession().update(UPDATE_PARENT_ADDRESS, pAddress) > 0;
	}

	@Override
	public ParentProfile queryParentById(final int pParentId) {
		return getSqlSession().selectOne(QUERY_PARENT_BY_ID, pParentId);
	}

	@Override
	public ParentProfile queryParentDetailById(final int pParentId) {
		return getSqlSession().selectOne(QUERY_PARENT_DETAIL_BY_ID, pParentId);
	}

	@Override
	public int initialProfile(BaseProfile pBaseProfile) {
		if (getSqlSession().insert(INITIAL_PROFILE, pBaseProfile) > 0) {
			return pBaseProfile.getId();
		}
		return 0;
	}

	@Override
	public boolean updateParentProfileDetail(final ParentProfile pParent) {
		return getSqlSession().update(UPDATE_PARENT_PROFILE_DETAIL, pParent) > 0;
	}
}
