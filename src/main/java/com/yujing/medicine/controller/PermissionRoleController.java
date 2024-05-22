package com.yujing.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yujing.medicine.utils.Result;
import com.yujing.medicine.pojo.PermissionMenu;
import com.yujing.medicine.pojo.PermissionRole;
import com.yujing.medicine.utils.Status;
import com.yujing.medicine.utils.TreeUtil;
import com.yujing.medicine.vo.PaginationResult;
import com.yujing.medicine.vo.PermissionInitVo;
import com.yujing.medicine.vo.PermissionRoleVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/14 13:35
 * 后台角色管理
*/
@RestController
@RequestMapping("/role")
public class PermissionRoleController extends BaseController {


    /**
     * 后台根据id查询查询角色
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(permissionRoleService.getById(id));
    }

    /**
     * 角色管理-查询全部角色
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.success(permissionRoleService.list());
    }
    /**
     * 角色管理-条件查询-分页
     * @param current
     * @param size
     * @param permissionRole
     * @return
     */
    @PostMapping("all")
    public Result search(@RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "5") Integer size,
                         @RequestBody(required = false) PermissionRole permissionRole){
        LambdaQueryWrapper<PermissionRole> wrapper = new LambdaQueryWrapper<>();
        if (permissionRole==null){
            permissionRole =new PermissionRole();
        }
        if (StringUtils.isNotBlank(permissionRole.getRoleName())){
            wrapper.like(PermissionRole::getRoleName,permissionRole.getRoleName());
        }
        if (permissionRole.getStatus()!=null && permissionRole.getStatus()!=0){
            wrapper.eq(PermissionRole::getStatus,permissionRole.getStatus());
        }
        Page<PermissionRole> page = new Page<>(current, size);
        IPage<PermissionRole> rolePage = permissionRoleMapper.selectPage(page, wrapper);
        //将查询出来的数据拷贝到vo里面进行返回页面
        ArrayList<PermissionRoleVo> voList = new ArrayList<>();
        for (PermissionRole record : rolePage.getRecords()) {
            PermissionRoleVo permissionRoleVo = new PermissionRoleVo();
            try {
                //将数据进行拷贝
                BeanUtils.copyProperties(permissionRoleVo,record);
                //使用枚举修改状态值
                permissionRoleVo.setStatus(Status.getStatusByCode(record.getStatus()).getCode());
                permissionRoleVo.setStatusDec(Status.getStatusByCode(record.getStatus()).getDescription());
                voList.add(permissionRoleVo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //使用分页对象将vo数据进行分页 然后返回页面
        PaginationResult<PermissionRoleVo> voPage = new PaginationResult<>(rolePage.getTotal(),rolePage.getCurrent(),rolePage.getSize(),voList);
        return Result.success(voPage);
    }

    /**
     * 角色管理-删除角色
     * @param roleId
     * @return
     */
    @PutMapping("/delete")
    public Result<PermissionRole> delete(@RequestParam("roleId") Long roleId){
        LambdaUpdateWrapper<PermissionRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PermissionRole::getId,roleId)
                .set(PermissionRole::getIsDeleted,1)
                .set(PermissionRole::getDeletedAt, LocalDateTime.now());
        boolean update = permissionRoleService.update(updateWrapper);
        if (update){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }

    }

    /**
     * 角色管理-修改状态
     * @param roleId
     * @param status
     * @return
     */
    @PutMapping("updateStatus")
    public Result updateStatus(@RequestParam("roleId") Long roleId,
                               @RequestParam("status") Integer status){
        //根据获取到的参数进行修改状态
        LambdaUpdateWrapper<PermissionRole> updateWrapper = new LambdaUpdateWrapper<>();
        //先进行判断 判断status的值
        if (status== Status.ENABLED.getCode()){ //如果状态为1,已启用状态
            updateWrapper.eq(PermissionRole::getId,roleId)
                    .set(PermissionRole::getStatus,Status.DISABLED.getCode());//修改为已禁用状态
        }else if (status== Status.DISABLED.getCode()){
            updateWrapper.eq(PermissionRole::getId,roleId)
                    .set(PermissionRole::getStatus,Status.ENABLED.getCode());
        }else if (status==Status.ALL.getCode()){//如果状态为0,未启用状态 修改为启用状态
            updateWrapper.eq(PermissionRole::getId,roleId)
                    .set(PermissionRole::getStatus,Status.ENABLED.getCode());
        }
        boolean update = permissionRoleService.update(updateWrapper);
        if (update){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }
    }


    /**
     * 后台添加角色
     * @param permissionRole
     * @param menuId
     * @return
     */
    @Transactional //添加事务 要么全部成功要么全部失败
    @PostMapping("/add")
    public Result<PermissionRole>add(@RequestBody PermissionRole permissionRole,@RequestParam("menuId") Long[] menuId){
        //先判断用户名是否存在
        LambdaQueryWrapper<PermissionRole> wrapper = new LambdaQueryWrapper();
        //判断角色名是不是空值
        if (permissionRole.getRoleName()==null && permissionRole.getRoleName()==""){
            return Result.error("角色名不能为空");
        }
        wrapper.eq(PermissionRole::getRoleName,permissionRole.getRoleName());
        PermissionRole role = permissionRoleService.getOne(wrapper);
        if (role != null){
            return Result.error("角色名已存在");
        }
        //给添加角色赋值默认状态
        permissionRole.setCreatedAt(LocalDateTime.now());
        //添加角色
        boolean flag = permissionRoleService.save(permissionRole);
        //获取到添加角色的最新Id
        Long newId =0L;
        if (flag){
             newId = permissionRole.getId();
        }
        //根据获取到的角色Id 和多个权限Id进行添加到角色权限表中数据  //添加角色权限表数据
        permissionRoleMenuService.addPermissionRoleMenuByRoleIdAndId(newId,menuId);
        if (flag){
            return Result.success("添加成功");
        }else {
            return Result.error("添加失败");
        }
    }


    /**
     * 根据角色id查询角色所有权限
     * @param roleId
     * @return
     */
    @PostMapping("/selectMenu")
    public @ResponseBody Object selectMenu(@RequestParam("roleId") Long roleId){
        HashMap<String, Object> map = new HashMap<>();
        //根据角色id查询角色拥有的所有权限
        List<PermissionMenu> menuList = permissionMenuMapper.selectPermissionMenuByRoleId(roleId);
        List<PermissionInitVo> voList = TreeUtil.buildTree(menuList);
        map.put("menuInfo",voList);
        return map;
    }

    /**
     *动态修改角色
     * 修改角色权限 需要先删除原有的角色权限 然后再进行添加
     * @param permissionRole
     * @param menuId
     * @return
     */
    @Transactional
    @PostMapping("/update")
    public Result<PermissionRole> update(@RequestBody PermissionRole permissionRole,
                                         @RequestParam("menuId") Long [] menuId){
        //1.先根据角色id 删除角色权限表中的所有数据
        permissionRoleMenuService.removeByRoleId(permissionRole.getId());
        //2.进行修改角色名称
        LambdaUpdateWrapper<PermissionRole> updateWrapper = new LambdaUpdateWrapper<>();
        if (permissionRole.getRoleName()!=null && !permissionRole.getRoleName().isEmpty()){
            updateWrapper.set(PermissionRole::getRoleName,permissionRole.getRoleName());
        }
        //添加修改时间
        updateWrapper.eq(PermissionRole::getId,permissionRole.getId())
                .set(PermissionRole::getUpdatedAt,LocalDateTime.now());
        boolean update = permissionRoleService.update(updateWrapper);
        //添加新的权限数据 根据角色id 和 权限id数组
        permissionRoleMenuService.addPermissionRoleMenuByRoleIdAndId(permissionRole.getId(),menuId);
        if (update){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }
    }



}
