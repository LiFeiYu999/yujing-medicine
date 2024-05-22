package com.yujing.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujing.medicine.pojo.PermissionRoleMenu;
import com.yujing.medicine.service.PermissionRoleMenuService;
import com.yujing.medicine.mapper.PermissionRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【permission_role_menu(后台权限-角色菜单功能表)】的数据库操作Service实现
* @createDate 2024-05-14 18:15:15
*/
@Service
public class PermissionRoleMenuServiceImpl extends ServiceImpl<PermissionRoleMenuMapper, PermissionRoleMenu>
    implements PermissionRoleMenuService{

    @Autowired
    private PermissionRoleMenuMapper permissionRoleMenuMapper;
    @Override
    public void addPermissionRoleMenuByRoleIdAndId(Long newId, Long[] menuId) {
        permissionRoleMenuMapper.insertPermissionRoleMenuByRoleIdAndMenuId(newId,menuId);
    }

    @Override
    public void removeByRoleId(Long id) {
        permissionRoleMenuMapper.deleteByRoleId(id);
    }
}




