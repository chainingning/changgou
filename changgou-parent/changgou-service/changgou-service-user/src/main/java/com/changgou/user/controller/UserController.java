package com.changgou.user.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.BCrypt;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * User分页条件搜索实现
     * @param user
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) User user, @PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页条件查询User
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * User分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页查询User
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param user
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<User>> findList(@RequestBody(required = false) User user) {
        //调用UserService实现条件查询User
        List<User> list = userService.findList(user);
        return new Result<List<User>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        //调用UserService实现根据主键删除
        userService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody User user, @PathVariable String id) {
        //设置主键值
        user.setUsername(id);
        //调用UserService实现修改User
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 新增User数据
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        //调用UserService实现添加User
        userService.add(user);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable String id) {
        //调用UserService实现根据主键查询User
        User user = userService.findById(id);
        return new Result<User>(true, StatusCode.OK, "查询成功", user);
    }

    /***
     * 查询User全部数据
     * @return
     */
    @GetMapping
    public Result<List<User>> findAll() {
        //调用UserService实现查询所有User
        List<User> list = userService.findAll();
        return new Result<List<User>>(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping("/login")
    public Result<User> login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        //1.从数据库中查询用户名对应的用户的对象
        User user = userService.findById(username);
        if (user == null) {
            //2.判断用户是否为空 为空返回数据
            return new Result<User>(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }

        //3如果不为空格 判断 密码是否正确 正确则登录成功

        if(BCrypt.checkpw(password,user.getPassword())){
            //成功
            Map<String,Object> info = new HashMap<String,Object>();
            info.put("role","USER");
            info.put("success","SUCCESS");
            info.put("username",username);

            //1.生成令牌
//            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info), null);
            String jwt = "JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info), null)";
            //2.设置cookie中
            Cookie cookie = new Cookie("Authorization",jwt);
            response.addCookie(cookie);
            //3.设置头文件中
            response.setHeader("Authorization",jwt);

            return new Result<User>(true, StatusCode.OK, "成功",jwt);
        }else{
            //失败
            return new Result<User>(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }






    }
}
