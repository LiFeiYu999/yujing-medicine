package com.yujing.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yujing.medicine.utils.Result;
import com.yujing.medicine.pojo.PermissionUser;
import com.yujing.medicine.pojo.PermissionUserRole;
import com.yujing.medicine.vo.PermissionUserDto;
import com.yujing.medicine.utils.MD5Util;
import com.yujing.medicine.utils.Status;
import com.yujing.medicine.vo.PermissionUserVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/14 10:38
 * 后台用户管理
 */
@RestController
@RequestMapping("/user")
public class PermissionUserController extends BaseController {


    /**
     * 用户管理-修改状态
     * @param userId
     * @param status
     * @return
     */
    @PutMapping("updateStatus")
    public Result updateStatus(@RequestParam("userId") Long userId,
                               @RequestParam("status") Integer status){
        //根据获取到的参数进行修改状态
        LambdaUpdateWrapper<PermissionUser> updateWrapper = new LambdaUpdateWrapper<>();
        //先进行判断 判断status的值
        if (status== Status.ENABLED.getCode()){ //如果状态为1,已启用状态
            updateWrapper.eq(PermissionUser::getId,userId)
                    .set(PermissionUser::getStatus,Status.DISABLED.getCode());//修改为已禁用状态
        }else if (status== Status.DISABLED.getCode()){
            updateWrapper.eq(PermissionUser::getId,userId)
                    .set(PermissionUser::getStatus,Status.ENABLED.getCode());
        }else if (status==Status.ALL.getCode()){//如果状态为0,未启用状态 修改为启用状态
            updateWrapper.eq(PermissionUser::getId,userId)
                    .set(PermissionUser::getStatus,Status.ENABLED.getCode());
        }
        boolean update = permissionUserService.update(updateWrapper);
        if (update){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }
    }

    /**
     * 用户管理-编辑用户
     * @param userId
     * @param userName
     * @param roleId
     * @return
     */
    @Transactional
    @PutMapping("/update")
    public Result update(@RequestParam("userId") Long userId,
                         @RequestParam("userName") String userName,
                         @RequestParam("roleId") Long roleId){
        LambdaUpdateWrapper<PermissionUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PermissionUser::getId,userId)
                .set(PermissionUser::getUserName,userName)
                .set(PermissionUser::getUpdatedAt,LocalDateTime.now());
        permissionUserService.update(updateWrapper);
        //修改用户表后 继续修改用户角色表中的数据
        LambdaUpdateWrapper<PermissionUserRole> updateWrapper1 = new LambdaUpdateWrapper<>();
        updateWrapper1.eq(PermissionUserRole::getUserId,userId)
                .set(PermissionUserRole::getCreatedAt,LocalDateTime.now())
                .set(PermissionUserRole::getRoleId,roleId);
        boolean update = permissionUserRoleService.update(updateWrapper1);
        if (update){
            return Result.success("编辑成功");
        }else {
            return Result.error("编辑失败");
        }

    }
    /**
     * 用户管理-新增用户
     * @return
     */
    @Transactional
    @PostMapping("/add")
    public Result add(@RequestParam("userName") String userName,
                      @RequestParam("password") String password,
                      @RequestParam("roleId") Long roleId) {
        //先判断用户名是否存在
        LambdaQueryWrapper<PermissionUser> wrapper = new LambdaQueryWrapper();
        wrapper.eq(PermissionUser::getUserName, userName);
        PermissionUser user = permissionUserService.getOne(wrapper);
        if (user != null) {
            return Result.error("用户名已存在");
        }
        //给对象进行赋值
        PermissionUser permissionUser = new PermissionUser();
        permissionUser.setUserName(userName);
        //给添加用户赋值默认状态);
        permissionUser.setStatus(Status.ENABLED.getCode());
        permissionUser.setCreatedAt(LocalDateTime.now());
        //对密码进行MD5加密
        String newPassword = MD5Util.hashPassword(password);
        permissionUser.setPassword(newPassword);
        int insert = permissionUserMapper.insert(permissionUser);
        Long newUserId= 0l;
        if (insert>0){
             newUserId = permissionUser.getId();//获取到新添加的用户id
        }
        //添加用户成功后 添加用户角色表
        PermissionUserRole userRole = new PermissionUserRole();
        userRole.setUserId(newUserId);
        userRole.setRoleId(roleId);
        int insert1 = permissionUserRoleMapper.insert(userRole);
        if (insert1>0){
            return Result.success("添加成功");
        }else {
            return Result.error("添加失败");
        }

    }

    /**
     * 用户管理-用户修改密码
     * @return
     */
    @PutMapping("/updatePassword")
    public Result update(@RequestParam("password") String password,
                         @RequestParam("newPassword") String newPassword,
                         @RequestParam("userId") Long userId) {
        //先判断两次输入的密码是否相等
        if (password.equals(newPassword)){
            //对新输入的密码进行加密
            String hashPassword = MD5Util.hashPassword(newPassword);
            //进行修改密码
            LambdaUpdateWrapper<PermissionUser> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(PermissionUser::getId,userId)
                    .set(PermissionUser::getPassword,hashPassword)
                    .set(PermissionUser::getUpdatedAt, LocalDateTime.now());
            boolean update = permissionUserService.update(updateWrapper);
            if(update){
                return Result.success("修改成功");
            }else {
                return Result.error("修改失败");
            }

        }else {
            return Result.error("两次输入的密码不一致");
        }
    }

    /**
     * 用户管理-删除用户
     * @param userId
     * @return
     */
    @PutMapping("/delete")
    public Result<PermissionUser> delete(@RequestParam("userId") Long userId) {
        LambdaUpdateWrapper<PermissionUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PermissionUser::getId,userId)
                .set(PermissionUser::getIsDeleted, 1)
                .set(PermissionUser::getDeletedAt, LocalDateTime.now());
        boolean update = permissionUserService.update(updateWrapper);
        if (update){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }

    }

    /**
     * 用户管理-条件查询-分页
     * @param current
     * @param size
     * @param permissionUserDto
     * @return
     */
    @PostMapping("/all")
    public Result all(
            @RequestBody(required = false) PermissionUserDto permissionUserDto,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "5") Integer size) {
        Page<PermissionUserVo> page = new Page<>(current, size);
        IPage<PermissionUserVo> userPage = permissionUserService.queryPageList(page, permissionUserDto);
        return Result.success(userPage);
    }



}
