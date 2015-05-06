package com.fmc.edu.service.auth;

import com.fmc.edu.manager.PermissionManager;
import com.fmc.edu.model.autho.User;
import org.apache.shiro.authc.*;
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

    /**
     * Do the permition authorize.
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

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

        String loginUser = (String) authenticationToken.getPrincipal();

        User user = new User();//userService.findByUsername(username);
        user.setId(1);
        user.setUsername("test");
        user.setPassword("123");

        if (user == null) {
            throw new UnknownAccountException();
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
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
}
