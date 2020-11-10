package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.pojo.Preferential;
import com.changgou.order.service.PreferentialService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:ning.chai@foxmail
 * @Description:
 *****/

@RestController
@RequestMapping("/preferential")
@CrossOrigin
public class PreferentialController {

    @Autowired
    private PreferentialService preferentialService;

    /***
     * Preferential分页条件搜索实现
     * @param preferential
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Preferential preferential, @PathVariable  int page, @PathVariable  int size){
        //调用PreferentialService实现分页条件查询Preferential
        PageInfo<Preferential> pageInfo = preferentialService.findPage(preferential, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Preferential分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用PreferentialService实现分页查询Preferential
        PageInfo<Preferential> pageInfo = preferentialService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param preferential
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Preferential>> findList(@RequestBody(required = false)  Preferential preferential){
        //调用PreferentialService实现条件查询Preferential
        List<Preferential> list = preferentialService.findList(preferential);
        return new Result<List<Preferential>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用PreferentialService实现根据主键删除
        preferentialService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Preferential数据
     * @param preferential
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Preferential preferential,@PathVariable Integer id){
        //设置主键值
        preferential.setId(id);
        //调用PreferentialService实现修改Preferential
        preferentialService.update(preferential);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Preferential数据
     * @param preferential
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Preferential preferential){
        //调用PreferentialService实现添加Preferential
        preferentialService.add(preferential);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Preferential数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Preferential> findById(@PathVariable Integer id){
        //调用PreferentialService实现根据主键查询Preferential
        Preferential preferential = preferentialService.findById(id);
        return new Result<Preferential>(true,StatusCode.OK,"查询成功",preferential);
    }

    /***
     * 查询Preferential全部数据
     * @return
     */
    @GetMapping
    public Result<List<Preferential>> findAll(){
        //调用PreferentialService实现查询所有Preferential
        List<Preferential> list = preferentialService.findAll();
        return new Result<List<Preferential>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
