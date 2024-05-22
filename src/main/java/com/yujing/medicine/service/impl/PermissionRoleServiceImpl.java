package com.yujing.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujing.medicine.mapper.PermissionMenuMapper;
import com.yujing.medicine.mapper.PermissionRoleMapper;
import com.yujing.medicine.mapper.PermissionRoleMenuMapper;
import com.yujing.medicine.pojo.PermissionRole;
import com.yujing.medicine.service.PermissionRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【permission_role(后台权限-角色表)】的数据库操作Service实现
* @createDate 2024-05-14 18:15:15
*/
@Service
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole>
    implements PermissionRoleService{
    @Autowired
    private PermissionMenuMapper permissionMenuMapper;
    @Autowired
    private PermissionRoleMenuMapper permissionRoleMenuMapper;

}




