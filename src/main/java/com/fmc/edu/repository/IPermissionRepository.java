package com.fmc.edu.repository;

import com.fmc.edu.model.autho.Permission;
import com.fmc.edu.model.autho.Role;

import java.util.List;

/**
 * Created by dylanyu on 5/6/2015.
 */
public interface IPermissionRepository {

    String FINED_ROLE_BY_IDENTITY = "com.fmc.edu.permission.findRoleByIdentity";

    List<Role> findRoleByIdentity(final int pIdentity);

    String FIND_PERMISSION_BY_ROLE_ID = "com.fmc.edu.permission.findPermissionByRoleId";

    List<Permission> findPermissionByRoleId(final int pRoleId);
}
