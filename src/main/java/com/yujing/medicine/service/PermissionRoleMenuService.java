package com.yujing.medicine.service;

import com.yujing.medicine.pojo.PermissionRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Acer
* @description 针对表【permission_role_menu(后台权限-角色菜单功能表)】的数据库操作Service
* @createDate 2024-05-14 18:15:15
*/
public interface PermissionRoleMenuService extends IService<PermissionRoleMenu> {

    /**
     * 根据角色id和多个权限id进行循环添加数据
     * @param newId
     * @param menuId
     */
    void addPermissionRoleMenuByRoleIdAndId(Long newId, Long[] menuId);

    /**
     * 根据角色id删除角色权限表中的所有数据
     * @param id
     */
    void removeByRoleId(Long id);
}
