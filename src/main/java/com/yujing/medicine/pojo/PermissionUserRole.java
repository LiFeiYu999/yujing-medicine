package com.yujing.medicine.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台权限-用户角色表
 * @TableName permission_user_role
 */
@TableName(value ="permission_user_role")
@Data
public class PermissionUserRole extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    @Override
    public String toString() {
        return "PermissionUserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", status=" + status +
                '}';
    }

    /**
     * 状态 0默认 1已启用 2已禁用
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}