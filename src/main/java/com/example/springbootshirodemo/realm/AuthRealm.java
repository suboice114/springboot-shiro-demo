package com.example.springbootshirodemo.realm;

import com.example.springbootshirodemo.model.Permission;
import com.example.springbootshirodemo.model.Role;
import com.example.springbootshirodemo.model.User;
import com.example.springbootshirodemo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("AuthorizationInfo*************");
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        //获取角色集合
        Set<Role> roleSet = user.getRoles();
        if(!CollectionUtils.isEmpty(roleSet)){
            //循环得到每个角色的权限
            for(Role role : roleSet){
                //保存到角色列表中
                roleList.add(role.getRname());
                System.out.println("角色:");
                System.out.println(role.getRname());
                //获取该角色的权限
                Set<Permission> permissionSet = role.getPermissions();
                if(!CollectionUtils.isEmpty(permissionSet)){
                    //把权限放到该账户的权限列表中
                    for (Permission permission : permissionSet){
                        System.out.println("权限:");
                        System.out.println(permission.getPname());
                        //存到权限列表中
                        permissionList.add(permission.getPname());
                    }
                }
            }
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleList);
        return info;
    }

    /**
     * 认证登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("***************AuthenticationInfo");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        System.out.println("AuthenticationInfo:"+user.toString());
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
