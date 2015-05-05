package com.fmc.edu.service.auth;

import com.fmc.edu.model.autho.Permission;
import com.fmc.edu.model.autho.Role;
import com.fmc.edu.model.autho.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by YW on 2015/5/4.
 */
@Service
public class FCMAuthorizingRealm extends AuthorizingRealm {
    /**
     * Do the permition authorize.
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Role role = new Role();
        role.setId(1l);
        role.setAvailable(true);
        role.setDescription("test");
        role.setRole("test");
        //role = userService.findRoles(username);
        Set<String> roles = new HashSet<String>();
        roles.add(role.getRole());
        authorizationInfo.setRoles(roles);
        Permission permission = new Permission();
        Set<String> permissions = new HashSet<String>();
        //userService.findPermissions(username)
        permissions.add("test:update");
        authorizationInfo.setStringPermissions(permissions);

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

        String username = (String) authenticationToken.getPrincipal();

        User user = new User();//userService.findByUsername(username);
        user.setId(1l);
        user.setUsername("test");
        user.setPassword("123");
        user.setLocked(false);
        user.setSalt("salt");

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
}
