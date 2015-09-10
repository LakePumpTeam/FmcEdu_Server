package com.fmc.edu.repository.impl;

import com.fmc.edu.model.autho.Permission;
import com.fmc.edu.model.autho.Role;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IPermissionRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylanyu on 5/6/2015.
 */
@Repository("permissionRepository")
public class PermissionRepository extends BaseRepository implements IPermissionRepository {

    @Override
    public List<Role> findRoleByUserId(int pUserId) {
        return getSqlSession().selectList(FINED_ROLES_BY_IDENTITY, pUserId);
    }

    @Override
    public List<Permission> findPermissionByRoleId(int pRoleId) {
        Permission permission = new Permission();
        permission.setId(1);
        permission.setAvailable(true);
        permission.setPermission("test:update");
        permission.setDescription("test");
        permission.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(permission);
        return permissions;
    }
}
