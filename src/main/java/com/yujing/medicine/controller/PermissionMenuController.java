package com.yujing.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yujing.medicine.jwt.JWTUtil;
import com.yujing.medicine.pojo.PermissionMenu;
import com.yujing.medicine.utils.Result;
import com.yujing.medicine.utils.Status;
import com.yujing.medicine.vo.PaginationResult;
import com.yujing.medicine.vo.PermissionInitVo;
import com.yujing.medicine.vo.PermissionMenuVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/16 15:52
 * 菜单管理操作
*/
@RestController
@RequestMapping("/menu")
public class PermissionMenuController extends BaseController {

    @GetMapping("token")
    public Result getToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return Result.success(token);
    }

    /**
     * 根据用户登录成功 初始化菜单列表
     * 根据当前登录用户获取所有左侧权限菜单
     * @return
     */
    @PostMapping("/init")
    public @ResponseBody Result init(HttpServletRequest request){
        //获取请求头登录的token信息
        String token = request.getHeader("token");//获取的token
        //进行解析
        Jws<Claims> claimsJws = JWTUtil.parseJWT(token);
        Claims claims = claimsJws.getBody();
        //获取到用户id
        Long userId = claims.get("id", Long.class);
        //根据用户id查询菜单权限集合
        List<PermissionInitVo> initVoList =permissionMenuService.findPermissionMenuList(userId);
        HashMap<String, Object> map = new HashMap<>();
        //根据登录成功后获取到用户名称
//        Subject subject = SecurityUtils.getSubject();//获取到主体
//        PrincipalCollection principals = subject.getPrincipals();//获取到登录用户信息
//        PermissionUser permissionUser = null;
        String roleName = permissionUserRoleMapper.selectRoleNamebyUserId(userId);
        map.put("roleName",roleName);
        //将查询到的数据存到map集合
        map.put("menuInfo",initVoList);
        return Result.success(map);
    }

    /**
     * 后台根据id查询菜单
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(permissionMenuService.getById(id));
    }

    /**
     * 删除菜单 就是逻辑删除改状态
     * @param menuId
     * @return
     */
    @PutMapping("/delete")
    public Result<PermissionMenu> delete(@RequestParam("menuId") Long menuId){
        LambdaUpdateWrapper<PermissionMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PermissionMenu::getId,menuId)
                .set(PermissionMenu::getIsDeleted,1)
                .set(PermissionMenu::getDeletedAt, LocalDateTime.now());
        boolean update = permissionMenuService.update(updateWrapper);
        if (update){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }

    }

    /**
     * 查询父级的菜单
     * @return
     */
    @PostMapping("/selectParent")
    public Result selectParent(){
        List<PermissionMenu> menuList=permissionMenuService.findParentNameByParentId();
        return Result.success(menuList);
    }

    /**
     * 菜单管理-修改状态
     * @param menuId
     * @param status
     * @return
     */
    @PutMapping("updateStatus")
    public Result updateStatus(@RequestParam("menuId") Long menuId,
                               @RequestParam("status") Integer status){
        //根据获取到的参数进行修改状态
        LambdaUpdateWrapper<PermissionMenu> updateWrapper = new LambdaUpdateWrapper<>();
        //先进行判断 判断status的值
        if (status== Status.ENABLED.getCode()){ //如果状态为1,已启用状态
            updateWrapper.eq(PermissionMenu::getId,menuId)
                    .set(PermissionMenu::getStatus,Status.DISABLED.getCode());//修改为已禁用状态
        }else if (status== Status.DISABLED.getCode()){
            updateWrapper.eq(PermissionMenu::getId,menuId)
                    .set(PermissionMenu::getStatus,Status.ENABLED.getCode());
        }else if (status==Status.ALL.getCode()){//如果状态为0,未启用状态 修改为启用状态
            updateWrapper.eq(PermissionMenu::getId,menuId)
                    .set(PermissionMenu::getStatus,Status.ENABLED.getCode());
        }
        boolean update = permissionMenuService.update(updateWrapper);
        if (update){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }
    }

    /**
     * 条件查询+分页+查询全部列表
     * @param current
     * @param size
     * @param permissionMenu
     * @return
     */
    @PostMapping("all")
    public Result search(@RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "5") Integer size,
                         @RequestBody(required = false) PermissionMenu permissionMenu){
        LambdaQueryWrapper<PermissionMenu> wrapper = new LambdaQueryWrapper<>();
        if (permissionMenu==null){
            permissionMenu=new PermissionMenu();
        }
        if (StringUtils.isNotBlank(permissionMenu.getMenuName())) {
            wrapper.like(PermissionMenu::getMenuName, permissionMenu.getMenuName());
        }
        if (permissionMenu.getStatus()!=null && permissionMenu.getStatus()!=0){
            wrapper.eq(PermissionMenu::getStatus,permissionMenu.getStatus());
        }
        if (permissionMenu.getLevel()!=null && permissionMenu.getLevel()!=0){
            wrapper.eq(PermissionMenu::getLevel,permissionMenu.getLevel());
        }
        Page<PermissionMenu> page = new Page<>(current, size);
        Page<PermissionMenu> menuPage = permissionMenuMapper.selectPage(page, wrapper);
        //将查询出来的数据拷贝到vo里面进行返回页面
        ArrayList<PermissionMenuVo> voList = new ArrayList<>();
        for (PermissionMenu record : menuPage.getRecords() ) {
            PermissionMenuVo permissionMenuVo = new PermissionMenuVo();
            try {
                //将数据进行拷贝
                BeanUtils.copyProperties(permissionMenuVo,record);
                //使用枚举修改状态
                permissionMenuVo.setStatus(Status.getStatusByCode(record.getStatus()).getCode());
                permissionMenuVo.setStatusDec(Status.getStatusByCode(record.getStatus()).getDescription());
                //获取到菜单id 然后根据自连接查询 查询子级id对应的父级菜单名称
                //SELECT c1.menu_name AS parent_name FROM permission_menu c1 JOIN permission_menu c2 ON c1.id = c2.parent_id WHERE c2.id =1;
                PermissionMenu menu = permissionMenuService.findParentNameById(record.getId());
                //查询出对象 进行判断
                if (menu==null){
                    permissionMenuVo.setParentId("");
                }else {
                    permissionMenuVo.setParentId(menu.getMenuName());
                }
                voList.add(permissionMenuVo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //使用分页对象将vo数据进行分页 返回页面
        PaginationResult<PermissionMenuVo> voPage = new PaginationResult<>(menuPage.getTotal(),menuPage.getCurrent(),menuPage.getSize(),voList);
        return Result.success(voPage);
    }
    /**
     * 后台添加菜单
     * @param permissionMenu
     * @return
     */
    @PostMapping("/add")
    public Result<PermissionMenu>add(@RequestBody PermissionMenu permissionMenu){
        //先判断用户名是否存在
        LambdaQueryWrapper<PermissionMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(PermissionMenu::getMenuName,permissionMenu.getMenuName());
        PermissionMenu menu = permissionMenuService.getOne(wrapper);
        //进行判断菜单名称不能重复
        if (menu != null){
            return Result.error("菜单已存在");
        }
        //给添加菜单赋值创建时间
        permissionMenu.setCreatedAt(LocalDateTime.now());
        boolean flag = permissionMenuService.save(permissionMenu);
        if (flag){
            return Result.success("添加成功");
        }else {
            return Result.error("添加失败");
        }

    }

    /**
     * 动态修改菜单权限
     * @param permissionMenu
     * @return
     */
    @PutMapping("/update")
    public Result<PermissionMenu> update(@RequestBody PermissionMenu permissionMenu){
        LambdaUpdateWrapper<PermissionMenu> updateWrapper = new LambdaUpdateWrapper<>();
        if (permissionMenu.getMenuName()!=null && !permissionMenu.getMenuName().isEmpty()){
            updateWrapper.set(PermissionMenu::getMenuName,permissionMenu.getMenuName());
        }
        if (permissionMenu.getParentId()!=null && permissionMenu.getParentId()!=0){
            updateWrapper.set(PermissionMenu::getParentId,permissionMenu.getParentId());
        }
        if (permissionMenu.getLevel()!=null && permissionMenu.getLevel()!=0){
            updateWrapper.set(PermissionMenu::getLevel,permissionMenu.getLevel());
        }
        if (permissionMenu.getSort()!=null && permissionMenu.getSort()!=0){
            updateWrapper.set(PermissionMenu::getSort,permissionMenu.getSort());
        }
        if (permissionMenu.getUrl()!=null && !permissionMenu.getUrl().isEmpty()){
            updateWrapper.set(PermissionMenu::getUrl,permissionMenu.getUrl());
        }
        if (permissionMenu.getIcon()!=null && !permissionMenu.getIcon().isEmpty()){
            updateWrapper.set(PermissionMenu::getIcon,permissionMenu.getIcon());
        }
        updateWrapper.eq(PermissionMenu::getId,permissionMenu.getId())
                .set(PermissionMenu::getUpdatedAt,LocalDateTime.now());
        boolean update = permissionMenuService.update(updateWrapper);
        if (update){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }

    }




}
