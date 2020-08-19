package com.changgou.content.feign;
import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.content.pojo.ContentCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:chaining
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="content")
@RequestMapping("/contentCategory")
public interface ContentCategoryFeign {

    /***
     * ContentCategory分页条件搜索实现
     * @param contentCategory
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult> findPage(@RequestBody(required = false) ContentCategory contentCategory, @PathVariable int page, @PathVariable int size);

    /***
     * ContentCategory分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult> findPage(@PathVariable int page, @PathVariable int size);

    /***
     * 多条件搜索品牌数据
     * @param contentCategory
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<ContentCategory>> findList(@RequestBody(required = false) ContentCategory contentCategory);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改ContentCategory数据
     * @param contentCategory
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody ContentCategory contentCategory, @PathVariable Long id);

    /***
     * 新增ContentCategory数据
     * @param contentCategory
     * @return
     */
    @PostMapping
    Result add(@RequestBody ContentCategory contentCategory);

    /***
     * 根据ID查询ContentCategory数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<ContentCategory> findById(@PathVariable Long id);

    /***
     * 查询ContentCategory全部数据
     * @return
     */
    @GetMapping
    Result<List<ContentCategory>> findAll();
}