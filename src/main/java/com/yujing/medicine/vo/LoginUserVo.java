package com.yujing.medicine.vo;

import lombok.Data;

@Data
public class LoginUserVo {
    private String userName; //用户名
    private String password; //密码
    private String checkCode; //验证码
}
