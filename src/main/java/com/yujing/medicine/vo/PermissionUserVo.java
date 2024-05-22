package com.yujing.medicine.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/17 9:54
 * 返回给页面的数据
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionUserVo {
    private Long userId;
    private String userName;//用户名
    private String roleName;//角色名
    private Integer status;//状态
    private String password;//密码
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;//结束时间




}
