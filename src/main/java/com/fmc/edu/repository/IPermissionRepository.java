package com.fmc.edu.repository;

import com.fmc.edu.model.autho.Permission;
import com.fmc.edu.model.autho.Role;

import java.util.List;

/**
 * Created by dylanyu on 5/6/2015.
 */
public interface IPermissionRepository {

	String FINED_ROLES_BY_IDENTITY = "com.fmc.edu.permission.findRolesByIdentity";

	String FIND_PERMISSION_BY_ROLE_ID = "com.fmc.edu.permission.findPermissionByRoleId";

	List<Role> findRoleByUserId(final int pUserId);

	List<Permission> findPermissionByRoleId(final int pRoleId);
}
