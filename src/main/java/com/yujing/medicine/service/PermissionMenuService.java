package com.yujing.medicine.service;

import com.yujing.medicine.pojo.PermissionMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yujing.medicine.vo.PermissionInitVo;

import java.util.List;

/**
* @author Acer
* @description 针对表【permission_menu(后台权限-菜单表)】的数据库操作Service
* @createDate 2024-05-14 18:15:15
*/
public interface PermissionMenuService extends IService<PermissionMenu> {

    List<PermissionMenu> selectTree();

    /**
     * 自连接查询 根据子类id查询父类的菜单名称
     * @param id
     * @return
     */
    PermissionMenu findParentNameById(Long id);

    /**
     * 查询父级菜单
     * @return
     */
    List<PermissionMenu> findParentNameByParentId();

    /**
     * 初始化菜单 查询登录用户所拥有的菜单
     * @return
     */
    List<PermissionInitVo> findPermissionMenuList(Long userId);
}
