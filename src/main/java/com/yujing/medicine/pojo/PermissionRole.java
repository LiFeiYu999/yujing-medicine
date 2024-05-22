package com.yujing.medicine.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台权限-角色表
 * @TableName permission_role
 */
@TableName(value ="permission_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRole extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Override
    public String toString() {
        return "PermissionRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 状态 0：默认 1：已启用 2：已删除 
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}