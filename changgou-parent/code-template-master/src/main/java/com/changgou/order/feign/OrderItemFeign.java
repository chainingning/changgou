package com.changgou.content.feign;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 * @Author:chaining
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="content")
@RequestMapping("/orderItem")
public interface OrderItemFeign {

    /***
     * OrderItem分页条件搜索实现
     * @param orderItem
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) OrderItem orderItem, @PathVariable  int page, @PathVariable  int size);

    /***
     * OrderItem分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param orderItem
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<OrderItem>> findList(@RequestBody(required = false) OrderItem orderItem);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改OrderItem数据
     * @param orderItem
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody OrderItem orderItem,@PathVariable String id);

    /***
     * 新增OrderItem数据
     * @param orderItem
     * @return
     */
    @PostMapping
    Result add(@RequestBody OrderItem orderItem);

    /***
     * 根据ID查询OrderItem数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<OrderItem> findById(@PathVariable String id);

    /***
     * 查询OrderItem全部数据
     * @return
     */
    @GetMapping
    Result<List<OrderItem>> findAll();
}