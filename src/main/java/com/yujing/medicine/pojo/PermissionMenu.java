package com.yujing.medicine.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台权限-菜单表
 * @TableName permission_menu
 */
@TableName(value ="permission_menu")
@Data
public class PermissionMenu extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父类权限id
     */
    private Long parentId;

    /**
     * 级别 1一级菜单 2二级菜单
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    @Override
    public String toString() {
        return "PermissionMenu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", sort=" + sort +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * 功能路径
     */
    private String url;

    /**
     * 显示图标
     */
    private String icon;

    /**
     * 状态 0默认 1已启用 2已禁用
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}