package com.fmc.edu.repository;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
public interface IParentRepository {

	String INITIAL_PARENT_PROFILE = "com.fmc.edu.profile.parent.initialParentProfile";

	boolean updateParentProfile(final ParentProfile pParentProfile);

	String INSERT_PARENT_PROFILE = "com.fmc.edu.profile.parent.insertParentProfile";

	int insertParentProfile(final ParentProfile pParentProfile);

	String INITIAL_PARENT_ADDRESS = "com.fmc.edu.profile.parent.persistParentAddress";

	boolean persistParentAddress(Address pAddress);

	String QUERY_PARENT_BY_PHONE = "com.fmc.edu.profile.parent.queryParentByPhone";

	ParentProfile queryParentByPhone(String pParentPhone);

	String INITIAL_PARENT_STUDENT_RELATIONSHIP = "com.fmc.edu.profile.parent.initialParentStudentRelationship";

	boolean initialParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship);

	String UPATE_PARENT_STUDENT_RELATIONSHIP = "com.fmc.edu.profile.parent.updateParentStudentRelationship";

	boolean updateParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship);


	String QUERY_PARENT_STUDENT_RELATIONSHIP = "com.fmc.edu.profile.parent.queryParentStudentRelationship";

	List<ParentStudentRelationship> queryParentStudentRelationship(Map<String, Object> pParentStudentRelationship);

	String QUERY_PARENTSTUDENTRELATION_BY_PARENTID_STUDENTID = "com.fmc.edu.profile.parent.queryParentStudentRelationship";

	ParentStudentRelationship queryParentStudentRelationship(final int parentId, final int studentId);

	String QUERY_ADDRESS_BY_PROFILE_ID = "com.fmc.edu.profile.parent.queryAddressByPhone";

	Address queryAddressByProfileId(int pProfileId);

	String UPDATE_PARENT_ADDRESS = "com.fmc.edu.profile.parent.updateParentAddress";

	boolean updateParentAddress(Address pAddress);

	String QUREY_PARENT_BY_ID = "com.fmc.edu.profile.parent.queryParentById";

	ParentProfile queryParentById(int pParentId);

	String QUREY_PARENT_DETAIL_BY_ID = "com.fmc.edu.profile.parent.queryParentDetailById";

	ParentProfile queryParentDetailById(int pParentId);

	String INITIAL_PROFILE = "com.fmc.edu.profile.parent.initialProfile";

	int initialProfile(BaseProfile pBaseProfile);
}
