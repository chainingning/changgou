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
@RequestMapping("/order")
public interface OrderFeign {

    /***
     * Order分页条件搜索实现
     * @param order
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) Order order, @PathVariable  int page, @PathVariable  int size);

    /***
     * Order分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param order
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Order>> findList(@RequestBody(required = false) Order order);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改Order数据
     * @param order
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Order order,@PathVariable String id);

    /***
     * 新增Order数据
     * @param order
     * @return
     */
    @PostMapping
    Result add(@RequestBody Order order);

    /***
     * 根据ID查询Order数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Order> findById(@PathVariable String id);

    /***
     * 查询Order全部数据
     * @return
     */
    @GetMapping
    Result<List<Order>> findAll();
}