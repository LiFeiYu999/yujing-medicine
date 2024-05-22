package com.yujing.medicine.mapper;

import com.yujing.medicine.pojo.PermissionUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Acer
* @description 针对表【permission_user_role(后台权限-用户角色表)】的数据库操作Mapper
* @createDate 2024-05-14 18:15:15
* @Entity com.yujing.medicine.pojo.PermissionUserRole
*/
@Mapper
public interface PermissionUserRoleMapper extends BaseMapper<PermissionUserRole> {

    PermissionUserRole selectRoleIdByUserId(@Param("id") Long id);

    /**
     * 根据用户id查询角色名称
     * @param id
     * @return
     */
    String selectRoleNamebyUserId(@Param("id") Long id);
}




