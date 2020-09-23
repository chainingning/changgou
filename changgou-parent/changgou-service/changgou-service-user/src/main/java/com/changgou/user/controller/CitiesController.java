package com.changgou.user.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    /***
     * Cities分页条件搜索实现
     * @param cities
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Cities cities, @PathVariable  int page, @PathVariable  int size){
        //调用CitiesService实现分页条件查询Cities
        PageInfo<Cities> pageInfo = citiesService.findPage(cities, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Cities分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用CitiesService实现分页查询Cities
        PageInfo<Cities> pageInfo = citiesService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param cities
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Cities>> findList(@RequestBody(required = false)  Cities cities){
        //调用CitiesService实现条件查询Cities
        List<Cities> list = citiesService.findList(cities);
        return new Result<List<Cities>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用CitiesService实现根据主键删除
        citiesService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Cities数据
     * @param cities
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Cities cities,@PathVariable String id){
        //设置主键值
        cities.setCityid(id);
        //调用CitiesService实现修改Cities
        citiesService.update(cities);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Cities数据
     * @param cities
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Cities cities){
        //调用CitiesService实现添加Cities
        citiesService.add(cities);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Cities数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Cities> findById(@PathVariable String id){
        //调用CitiesService实现根据主键查询Cities
        Cities cities = citiesService.findById(id);
        return new Result<Cities>(true,StatusCode.OK,"查询成功",cities);
    }

    /***
     * 查询Cities全部数据
     * @return
     */
    @GetMapping
    public Result<List<Cities>> findAll(){
        //调用CitiesService实现查询所有Cities
        List<Cities> list = citiesService.findAll();
        return new Result<List<Cities>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
