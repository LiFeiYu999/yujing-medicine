package com.yujing.medicine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.vo.PermissionUserDto;
import com.yujing.medicine.vo.PermissionUserVo;

/**
* @author Acer
* @description 针对表【permission_user(后台权限-用户表)】的数据库操作Service
* @createDate 2024-05-14 18:15:15
*/
public interface PermissionUserService extends IService<PermissionUser> {

    //根据用户名查询信息
    PermissionUser getUserByName(String userName);


    IPage<PermissionUserVo> queryPageList(Page<PermissionUserVo> page, PermissionUserDto permissionUserDto);
}
