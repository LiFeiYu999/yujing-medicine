package com.yujing.medicine.controller;

import com.yujing.medicine.mapper.PermissionMenuMapper;
import com.yujing.medicine.mapper.PermissionRoleMapper;
import com.yujing.medicine.mapper.PermissionUserMapper;
import com.yujing.medicine.mapper.PermissionUserRoleMapper;
import com.yujing.medicine.service.PermissionRoleMenuService;
import com.yujing.medicine.service.PermissionUserRoleService;
import com.yujing.medicine.service.impl.PermissionLoginCaptchaServiceImpl;
import com.yujing.medicine.service.impl.PermissionMenuServiceImpl;
import com.yujing.medicine.service.impl.PermissionRoleServiceImpl;
import com.yujing.medicine.service.impl.PermissionUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/16 10:35
 * 调用方法工具类
*/
public class BaseController {

    //用户
    @Autowired
    PermissionUserServiceImpl permissionUserService;
    @Autowired
    PermissionUserMapper permissionUserMapper;
    //角色
    @Autowired
    PermissionRoleServiceImpl permissionRoleService;
    @Autowired
    PermissionRoleMapper permissionRoleMapper;
    //菜单
    @Autowired
    PermissionMenuMapper permissionMenuMapper;
    @Autowired
    PermissionMenuServiceImpl permissionMenuService;
    //用户角色
    @Autowired
    PermissionUserRoleMapper permissionUserRoleMapper;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;
    @Autowired
    PermissionRoleMenuService permissionRoleMenuService;
    @Autowired
    PermissionLoginCaptchaServiceImpl permissionLoginCaptchaService;//添加验证码
}
