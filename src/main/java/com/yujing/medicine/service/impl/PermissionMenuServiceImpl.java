package com.yujing.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujing.medicine.mapper.PermissionMenuMapper;
import com.yujing.medicine.mapper.PermissionUserRoleMapper;
import com.yujing.medicine.pojo.PermissionMenu;
import com.yujing.medicine.pojo.PermissionUserRole;
import com.yujing.medicine.service.PermissionMenuService;
import com.yujing.medicine.utils.TreeUtil;
import com.yujing.medicine.vo.PermissionInitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Acer
* @description 针对表【permission_menu(后台权限-菜单表)】的数据库操作Service实现
* @createDate 2024-05-14 18:15:15
*/
@Service
public class PermissionMenuServiceImpl extends ServiceImpl<PermissionMenuMapper, PermissionMenu>
    implements PermissionMenuService{

    @Autowired
    private PermissionMenuMapper permissionMenuMapper;
    @Autowired
    private PermissionUserRoleMapper permissionUserRoleMapper;

    @Override
    public List<PermissionMenu> selectTree() {
        return null;
    }

    @Override
    public PermissionMenu findParentNameById(Long id) {
        return permissionMenuMapper.selectParentNameById(id);
    }

    @Override
    public List<PermissionMenu> findParentNameByParentId() {
        return permissionMenuMapper.selectParentNameByParentId();
    }

    @Override
    public List<PermissionInitVo> findPermissionMenuList(Long userId) {
        PermissionUserRole permissionUserRole = permissionUserRoleMapper.selectRoleIdByUserId(userId);
        //根据用户id得到角色id  根据角色id获取到角色权限表中的菜单id
        List<PermissionMenu> menuList = permissionMenuMapper.selectPermissionMenuByRoleId(permissionUserRole.getRoleId());
        //将获取到的数据根据父id进行父子树状排序
        List<PermissionInitVo> initVoList = TreeUtil.buildTree(menuList);
        return initVoList;
    }


}




