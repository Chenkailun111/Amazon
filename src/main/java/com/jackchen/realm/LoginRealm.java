package com.jackchen.realm;

import com.jackchen.pojo.User;
import com.jackchen.service.UserLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacey on 2017/6/30.
 */

@Component
public class LoginRealm extends AuthorizingRealm{

  @Autowired
   private UserLoginService userLoginService;

    /**
     * jack 授权
     * 获取身份信息，我们可以在这个方法中，从数据库获取该用户的权限和角色信息
     *     当调用权限验证时，就会调用此方法
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.out.println("授权--->");
        //sys_user   sys_role   sys_user_role
        //sys_menu  sys_role_menu
        // System.out.println("principals----->"+principals);

        User user = (User) principals.getPrimaryPrincipal();
        //SecurityUtils.getSubject().getPrincipal(); 使用该方法也可以得到认证后存储的对象
        //List<String> roles = roleService.findRoleByUserId(user.getUserId());
        //List<String> perms = menuService.findPermsByUserId(user.getUserId());
        //授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
       /* info.addRoles(roles);
        info.addStringPermissions(perms);*/
       List<String> roles = new ArrayList<>();
       roles.add("admin");
       roles.add("user");
       info.addRoles(roles);
        return info;
    }

    /**
     * 在这个方法中，进行身份验证
     *         login时调用
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证---->");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();
        String password  = new String(usernamePasswordToken.getPassword());

        //跟真实数据库数据做比较
        User sysUser = userLoginService.findByName(username);
        if (sysUser==null){
            throw  new UnknownAccountException("账号不存在");
        }
        if (!sysUser.getPwd().equals(password)){
            throw  new IncorrectCredentialsException("密码错误");
        }

        //封装AuthenticationInfo 对象
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser,password,this.getName());
        return info;
    }
}
