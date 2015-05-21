package com.fmc.edu.service.auth;

import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.PermissionManager;
import com.fmc.edu.model.profile.BaseProfile;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by YW on 2015/5/4.
 */
@Service
public class FCMAuthorizingRealm extends AuthorizingRealm {
	@Resource(name = "permissionManager")
	private PermissionManager mPermissionManager;
	@Resource(name = "myAccountManager")
	private MyAccountManager mMyAccountManager;

	/**
	 * Do the permission authorize.
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		if (principalCollection == null) {
			throw new AuthorizationException("Principal can not be null");
		}
		//int userId = (int) principalCollection.iterator().next();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//TODO Should input real user id
		Set<String> role = getPermissionManager().getRolesForUser(0);
		authorizationInfo.setRoles(role);
		//TODO Should input real user id
		authorizationInfo.setStringPermissions(getPermissionManager().getPermissonsByUserId(0));

		return authorizationInfo;
	}

	/**
	 * Authenticate the login.
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

		String phone = (String) authenticationToken.getPrincipal();

		BaseProfile user = getMyAccountManager().findUser(phone);

		if (user == null) {
			throw new UnknownAccountException(MyAccountManager.ERROR_NOT_FIND_USER);
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getPhone(),
				user.getPassword(),
				getName()
		);
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	public PermissionManager getPermissionManager() {
		return mPermissionManager;
	}

	public void setPermissionManager(PermissionManager pPermissionManager) {
		mPermissionManager = pPermissionManager;
	}

	public MyAccountManager getMyAccountManager() {
		return mMyAccountManager;
	}

	public void setMyAccountManager(MyAccountManager pMyAccountManager) {
		mMyAccountManager = pMyAccountManager;
	}
}
