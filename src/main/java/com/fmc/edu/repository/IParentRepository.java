package com.fmc.edu.repository;

import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;

/**
 * Created by Yove on 5/8/2015.
 */
public interface IParentRepository {

	String INITIAL_PARENT_PROFILE = "com.fmc.edu.profile.parent.initialParentProfile";

	boolean initialParentProfile(final ParentProfile pParentProfile);

	String INITIAL_PARENT_ADDRESS = "com.fmc.edu.profile.parent.persistParentAddress";

	boolean persistParentAddress(Address pAddress);

	String QUERY_PARENT_BY_PHONE = "com.fmc.edu.profile.parent.queryParentByPhone";

	ParentProfile queryParentByPhone(String pParentPhone);

	String INITIAL_PARENT_STUDENT_RELATIONSHIP = "com.fmc.edu.profile.parent.initialParentStudentRelationship";

	boolean initialParentStudentRelationship(ParentStudentRelationship pParentStudentRelationship);

	String QUERY_ADDRESS_BY_PHONE = "com.fmc.edu.profile.parent.queryAddressByPhone";

	Address queryAddressByPhone(String pPhoneNumber);

	String UPDATE_PARENT_ADDRESS = "com.fmc.edu.profile.parent.updateParentAddress";

	boolean updateParentAddress(Address pAddress);

	String QUREY_PARENT_BY_ID = "com.fmc.edu.profile.parent.queryParentById";

	ParentProfile queryParentById(int pParentId);
}
