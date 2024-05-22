package com.yujing.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yujing.medicine.pojo.PermissionRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Acer
* @description 针对表【permission_role(后台权限-角色表)】的数据库操作Mapper
* @createDate 2024-05-14 18:15:15
* @Entity com.yujing.medicine.pojo.PermissionRole
*/
@Mapper
public interface PermissionRoleMapper extends BaseMapper<PermissionRole> {

    /**
     * 根据用户名称查询所有角色信息
     * @param username
     * @return
     */
    List<PermissionRole> getAllRoleListByUserName(@Param("userName") String username);

    /**
     * 根据角色id查询数据
     * @param roleId
     * @return
     */
    PermissionRole getRoleNameById(Integer roleId);

}




