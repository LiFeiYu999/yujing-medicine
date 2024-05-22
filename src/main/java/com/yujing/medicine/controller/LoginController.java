package com.yujing.medicine.controller;


import com.yujing.medicine.jwt.JWTUtil;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.utils.MD5Util;
import com.yujing.medicine.utils.Result;
import com.yujing.medicine.vo.LoginUserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/14 14:26
 * 后台登入退出功能
*/
@RestController
public class LoginController extends BaseController{

    /**
     * 后台用户登入
     * @param
     * @param loginUserVo
     * @return
     */
    @PostMapping("/login")
    public Result<PermissionUser> login(@RequestBody LoginUserVo loginUserVo,HttpServletRequest request) {
        //从session中获取验证码数据
        HttpSession session = request.getSession();
        Object getCode = session.getAttribute("getCode");
        System.out.println("session中的验证码"+getCode);
        System.out.println("输入"+loginUserVo.getCheckCode());
//        if (!loginUserVo.getCheckCode().equalsIgnoreCase(getCode)){
//            //验证码不正确
//            return Result.error("验证码不正确");
//        }
        //根据用户名称获取用户的状态
        PermissionUser user = permissionUserService.getUserByName(loginUserVo.getUserName());
        System.out.println("user = " + user);
        String username = loginUserVo.getUserName();
        //对用户输入的密码进行再次的MD5加密
        String password = MD5Util.hashPassword(loginUserVo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        if (user.getStatus()==2 || user.getStatus()==0){
            subject.logout();
            return Result.error("用户已禁用");
        }
        try {
            subject.login(token);
            //根据用户名和查询到的用户id生成jwt
            String jwt = JWTUtil.createJWT(loginUserVo.getUserName(),user.getId());
            Map<String,Object> map = new HashMap<>();
            //根据用户名和id生成的token传给前端
            map.put("token",jwt);
            map.put("userName",loginUserVo.getUserName());
            //这个是获取当前管理系统后台的登入人对象,根据名称进行来添加用户
            return Result.success(map);
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码错误");
        } catch (UnknownAccountException e) {
            return Result.error("账号不存在");
        }

    }
    /**
     * 后台用户退出
     * @return
     */
    @PostMapping("/logout")
    public Result<PermissionUser> logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//注销当前用户
        return Result.success("成功退出");
    }



}
