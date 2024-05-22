package com.yujing.medicine.utils;

import com.yujing.medicine.pojo.PermissionMenu;
import com.yujing.medicine.vo.PermissionInitVo;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/20 15:39
 * 转换数据成树状菜单工具类
*/
public class TreeUtil {
    /**
     * 将获取到的数据进行树状排序 父id进行存储
     * @param menuList
     * @return
     */
    public static List<PermissionInitVo> buildTree(List<PermissionMenu> menuList) {
        List<PermissionInitVo> voList = new ArrayList<>();
        if (menuList!=null && menuList.size()!=0){
            for (PermissionMenu menu : menuList) {
                //判断parentId是否为0 就是父id
                if (menu.getParentId() == 0){
                    PermissionInitVo initVo = new PermissionInitVo();
                    try {
                        //将获取到的数据拷贝到initVo中
                        BeanUtils.copyProperties(initVo,menu);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    voList.add(findChildren(initVo,menuList));
                }
            }
        }
        return voList;
    }

    /**
     * 判断父级菜单有没有子级菜单 把子级菜单存储到父级中
     * @param initVo
     * @param menuList
     * @return
     */
    public static PermissionInitVo findChildren(PermissionInitVo initVo, List<PermissionMenu> menuList) {
        initVo.setChildren(new ArrayList<>());
        for (PermissionMenu menu : menuList) {
            //判断parentId不为0 就是子级菜单
            if (menu.getParentId()!=0){
                if (menu.getParentId().equals(initVo.getId())){
                    PermissionInitVo vo = new PermissionInitVo();
                    try {
                        BeanUtils.copyProperties(vo,menu);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    PermissionInitVo item =findChildren(vo,menuList);
                    initVo.getChildren().add(item);
                }
            }
        }
        return initVo;
    }
}
