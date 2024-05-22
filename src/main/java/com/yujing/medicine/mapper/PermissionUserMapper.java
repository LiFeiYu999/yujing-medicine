package com.yujing.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.vo.PermissionUserDto;
import com.yujing.medicine.vo.PermissionUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author Acer
 * @description 针对表【permission_user(后台权限-用户表)】的数据库操作Mapper
 * @createDate 2024-05-14 18:15:15
 * @Entity com.yujing.medicine.pojo.PermissionUser
 */
@Mapper
public interface PermissionUserMapper extends BaseMapper<PermissionUser> {

    PermissionUser selectUserByName(String userName);

    IPage<PermissionUserVo> selectPageList(@Param("page") Page<PermissionUserVo> page, @Param("user") PermissionUserDto dto);

    /**
     * 根据名称查询id
     * @param userName
     * @return
     */
    PermissionUser selectIdByName(PrincipalCollection userName);
}




