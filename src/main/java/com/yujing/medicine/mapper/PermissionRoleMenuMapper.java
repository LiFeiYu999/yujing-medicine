package com.yujing.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yujing.medicine.pojo.PermissionRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Acer
* @description 针对表【permission_role_menu(后台权限-角色菜单功能表)】的数据库操作Mapper
* @createDate 2024-05-14 18:15:15
* @Entity com.yujing.medicine.pojo.PermissionRoleMenu
*/
@Mapper
public interface PermissionRoleMenuMapper extends BaseMapper<PermissionRoleMenu> {

    List<Long> selectParentByRoleId();

    void insertPermissionRoleMenuByRoleIdAndMenuId(@Param("newId") Long newId, @Param("menuId") Long[] menuId);

    void deleteByRoleId(@Param("id") Long id);
}




