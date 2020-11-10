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
@RequestMapping("/returnOrder")
public interface ReturnOrderFeign {

    /***
     * ReturnOrder分页条件搜索实现
     * @param returnOrder
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) ReturnOrder returnOrder, @PathVariable  int page, @PathVariable  int size);

    /***
     * ReturnOrder分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param returnOrder
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<ReturnOrder>> findList(@RequestBody(required = false) ReturnOrder returnOrder);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改ReturnOrder数据
     * @param returnOrder
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody ReturnOrder returnOrder,@PathVariable Long id);

    /***
     * 新增ReturnOrder数据
     * @param returnOrder
     * @return
     */
    @PostMapping
    Result add(@RequestBody ReturnOrder returnOrder);

    /***
     * 根据ID查询ReturnOrder数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<ReturnOrder> findById(@PathVariable Long id);

    /***
     * 查询ReturnOrder全部数据
     * @return
     */
    @GetMapping
    Result<List<ReturnOrder>> findAll();
}