package com.shiro.config;

import com.shiro.bean.Permissions;
import com.shiro.bean.Role;
import com.shiro.bean.User;
import com.shiro.service.LoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by dell on 2020/9/22.
 */
public class MyRealm extends AuthorizingRealm {


    @Autowired
    private LoginServiceImpl loginService;


    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = loginService.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }


    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    //实现简单的认证实现简单的认证操作即可，不做授权
    //用户名必须是 javaboy ，用户密码必须是 123 ，满足这样的条件，就能登录成功！
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

//        String username = (String) token.getPrincipal();
//        System.out.println("token.getPrincipal("+token);
//
//        System.out.println("入参的为"+username);
//        if (!"javaboy".equals(username)) {
//            throw new UnknownAccountException("账户不存在!");
//        }
//        return new SimpleAuthenticationInfo(username, "123", getName());


        if (StringUtils.isEmpty(token.getPrincipal())) {
            return null;
        }
        //获取用户信息
        String name = token.getPrincipal().toString();
           System.out.println("入参的为name"+name);

        User user = loginService.getUserByName(name);

        if (user == null) {
            //这里返回后会报出对应异常
            //return null;
            throw new UnknownAccountException("账户不存在!");
        }




            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;

    }





}

