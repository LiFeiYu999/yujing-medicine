package com.yujing.medicine.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/17 9:55
 * 从添加接受到的数据 进行查询
*/
@Data
public class PermissionUserDto implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private String userName;

    private Long roleId;

    /**
     * 状态 0：默认 1：已启用 2：已禁用
     */
    private Integer status;

    /**
     * 创建时间_开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdStart;

    /**
     * 创建时间_结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdEnd;



}