package com.yujing.medicine.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujing.medicine.mapper.PermissionUserMapper;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.vo.PermissionUserDto;
import com.yujing.medicine.service.PermissionUserService;
import com.yujing.medicine.vo.PermissionUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【permission_user(后台权限-用户表)】的数据库操作Service实现
* @createDate 2024-05-14 18:15:15
*/
@Service
public class PermissionUserServiceImpl extends ServiceImpl<PermissionUserMapper, PermissionUser>
    implements PermissionUserService{

    @Autowired
    private PermissionUserMapper permissionUserMapper;

    @Override
    public PermissionUser getUserByName(String userName) {
        return permissionUserMapper.selectUserByName(userName);
    }

    @Override
    public IPage<PermissionUserVo> queryPageList(Page<PermissionUserVo> page, PermissionUserDto permissionUserDto) {
        return permissionUserMapper.selectPageList(page,permissionUserDto);
    }


}




