package com.yujing.medicine.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台权限-用户表
 * @TableName permission_user
 */
@TableName(value ="permission_user")
@Data
public class PermissionUser extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    @Override
    public String toString() {
        return "PermissionUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
    /**
     * 状态 0：默认 1：已启用 2：已禁用
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}