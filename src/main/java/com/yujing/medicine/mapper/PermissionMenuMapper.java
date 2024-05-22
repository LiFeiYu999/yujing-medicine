package com.yujing.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yujing.medicine.pojo.PermissionMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Acer
* @description 针对表【permission_menu(后台权限-菜单表)】的数据库操作Mapper
* @createDate 2024-05-14 18:15:15
* @Entity com.yujing.medicine.pojo.PermissionMenu
*/
@Mapper
public interface PermissionMenuMapper extends BaseMapper<PermissionMenu> {
    /**
     * 根据用户名查询所有权限
     * @param userName
     * @return
     */
    List<PermissionMenu> getAllPermissionListByUserName(@Param("userName") String userName);

    PermissionMenu selectParentNameById(@Param("id") Long id);

    List<PermissionMenu> selectParentNameByParentId();

    List<PermissionMenu> selectPermissionByParentId(@Param("parentId") Integer parentId);

    List<PermissionMenu> selectPermissionMenuByRoleId(@Param("roleId") Long roleId);
}




