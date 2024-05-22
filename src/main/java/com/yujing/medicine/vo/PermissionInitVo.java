package com.yujing.medicine.vo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionInitVo {
    private Long id; //权限id
    private String menuName;//菜单名称
    private String icon; //菜单图片
    private String url;//跳转路径
    private List<PermissionInitVo> children;//子级菜单
}
