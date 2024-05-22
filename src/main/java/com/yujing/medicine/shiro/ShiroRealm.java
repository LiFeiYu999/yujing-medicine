package com.yujing.medicine.shiro;

import com.yujing.medicine.mapper.PermissionMenuMapper;
import com.yujing.medicine.mapper.PermissionRoleMapper;
import com.yujing.medicine.pojo.PermissionMenu;
import com.yujing.medicine.pojo.PermissionRole;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.service.PermissionUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/15 18:51
*/
@Service
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PermissionRoleMapper permissionRoleMapper;//角色mapper
    @Autowired
    private PermissionMenuMapper permissionMenuMapper;//菜单mapper
    @Resource
    private PermissionUserService permissionUserService;//用户mapper


    /**
     * 为当前登录用户授予角色和权限
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        logger.info("username"+username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //判断给超级管理员设置全部权限
        if ("admin".equals(username)){
            //返回AuthorizationInfo授权类的子类
            info.addStringPermission("*:*");
            return info;
        }else {
            //2.根据用户名查询用户所有的角色信息
            Set<String> roleSet = new HashSet<>();//创建一个set存储名字
            List<PermissionRole> roleList = permissionRoleMapper.getAllRoleListByUserName(username);
            for (PermissionRole role : roleList) {
                String roleName = role.getRoleName();
                roleSet.add(roleName);
            }
            info.setRoles(roleSet);
            //3.根据用户名查询用户所有的权限信息
            HashSet<String> menuSet = new HashSet<>();
            List<PermissionMenu> menuList = permissionMenuMapper.getAllPermissionListByUserName(username);
            for (PermissionMenu menu : menuList) {
                String menuName = menu.getMenuName();
                menuSet.add(menuName);
            }
            info.setStringPermissions(menuSet);
            logger.info("用户：{}拥有的权限有：{}", username, menuSet);
            return info;
        }
    }
    /**
     * 验证当前登录的用户
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo："  + authenticationToken);
        //获取到token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取到用户名
        String userName = token.getUsername();
        //根据用户名获取user对象数据
        PermissionUser user = permissionUserService.getUserByName(userName);
        logger.info("user:{}",user);
        if (user!=null){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return info;
        }else {
            return null;
        }
    }
}
