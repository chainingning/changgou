package com.changgou.order.service;

import com.changgou.order.pojo.CategoryReport;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/****
 * @Author:chaining
 * @Description:CategoryReport业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface CategoryReportService {

    /***
     * CategoryReport多条件分页查询
     * @param categoryReport
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryReport> findPage(CategoryReport categoryReport, int page, int size);

    /***
     * CategoryReport分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<CategoryReport> findPage(int page, int size);

    /***
     * CategoryReport多条件搜索方法
     * @param categoryReport
     * @return
     */
    List<CategoryReport> findList(CategoryReport categoryReport);

    /***
     * 删除CategoryReport
     * @param id
     */
    void delete(Date id);

    /***
     * 修改CategoryReport数据
     * @param categoryReport
     */
    void update(CategoryReport categoryReport);

    /***
     * 新增CategoryReport
     * @param categoryReport
     */
    void add(CategoryReport categoryReport);

    /**
     * 根据ID查询CategoryReport
     * @param id
     * @return
     */
     CategoryReport findById(Date id);

    /***
     * 查询所有CategoryReport
     * @return
     */
    List<CategoryReport> findAll();
}
