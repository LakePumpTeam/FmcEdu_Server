package com.fmc.edu.manager;

import com.fmc.edu.model.autho.Permission;
import com.fmc.edu.model.autho.Role;
import com.fmc.edu.repository.IPermissionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dylanyu on 5/6/2015.
 */
@Service(value = "permissionManager")
public class PermissionManager {
	@Resource(name = "permissionRepository")
	private IPermissionRepository mPermissionRepository;

	public Set<String> getRolesForUser(final int pUserId) {
		List<Role> roleList = getPermissionRepository().findRoleByUserId(pUserId);
		Set<String> roleNames = new HashSet<String>();
		for (Role role : roleList) {
			roleNames.add(role.getRole());
		}
		return roleNames;
	}

	public List<String> getPermissionsByRoleId(final int pRoleId) {
		List<Permission> permissions = getPermissionRepository().findPermissionByRoleId(pRoleId);
		List<String> permissionNames = new ArrayList<String>(permissions.size());
		for (Permission permission : permissions) {
			permissionNames.add(permission.getPermission());
		}
		return permissionNames;
	}

	public Set<String> getPermissionsByUserId(final int pUserId) {
		List<Role> roleList = getPermissionRepository().findRoleByUserId(pUserId);
		Set<String> roleNames = new HashSet<String>();
		Set<String> grantPermissions = new HashSet<String>();
		for (Role role : roleList) {
			grantPermissions.addAll(getPermissionsByRoleId(role.getId()));
		}
		return grantPermissions;
	}

	public IPermissionRepository getPermissionRepository() {

		return mPermissionRepository;
	}

	public void setPermissionRepository(IPermissionRepository pPermissionRepository) {
		mPermissionRepository = pPermissionRepository;
	}
}
