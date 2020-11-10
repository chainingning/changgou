package com.changgou.order.service.impl;
import com.changgou.order.dao.CategoryReportMapper;
import com.changgou.order.pojo.CategoryReport;
import com.changgou.order.service.CategoryReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:chaining
 * @Description:CategoryReport业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class CategoryReportServiceImpl implements CategoryReportService {

    @Autowired
    private CategoryReportMapper categoryReportMapper;


    /**
     * CategoryReport条件+分页查询
     * @param categoryReport 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<CategoryReport> findPage(CategoryReport categoryReport, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(categoryReport);
        //执行搜索
        return new PageInfo<CategoryReport>(categoryReportMapper.selectByExample(example));
    }

    /**
     * CategoryReport分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<CategoryReport> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<CategoryReport>(categoryReportMapper.selectAll());
    }

    /**
     * CategoryReport条件查询
     * @param categoryReport
     * @return
     */
    @Override
    public List<CategoryReport> findList(CategoryReport categoryReport){
        //构建查询条件
        Example example = createExample(categoryReport);
        //根据构建的条件查询数据
        return categoryReportMapper.selectByExample(example);
    }


    /**
     * CategoryReport构建查询对象
     * @param categoryReport
     * @return
     */
    public Example createExample(CategoryReport categoryReport){
        Example example=new Example(CategoryReport.class);
        Example.Criteria criteria = example.createCriteria();
        if(categoryReport!=null){
            // 1级分类
            if(!StringUtils.isEmpty(categoryReport.getCategoryId1())){
                    criteria.andEqualTo("categoryId1",categoryReport.getCategoryId1());
            }
            // 2级分类
            if(!StringUtils.isEmpty(categoryReport.getCategoryId2())){
                    criteria.andEqualTo("categoryId2",categoryReport.getCategoryId2());
            }
            // 3级分类
            if(!StringUtils.isEmpty(categoryReport.getCategoryId3())){
                    criteria.andEqualTo("categoryId3",categoryReport.getCategoryId3());
            }
            // 统计日期
            if(!StringUtils.isEmpty(categoryReport.getCountDate())){
                    criteria.andEqualTo("countDate",categoryReport.getCountDate());
            }
            // 销售数量
            if(!StringUtils.isEmpty(categoryReport.getNum())){
                    criteria.andEqualTo("num",categoryReport.getNum());
            }
            // 销售额
            if(!StringUtils.isEmpty(categoryReport.getMoney())){
                    criteria.andEqualTo("money",categoryReport.getMoney());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Date id){
        categoryReportMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改CategoryReport
     * @param categoryReport
     */
    @Override
    public void update(CategoryReport categoryReport){
        categoryReportMapper.updateByPrimaryKey(categoryReport);
    }

    /**
     * 增加CategoryReport
     * @param categoryReport
     */
    @Override
    public void add(CategoryReport categoryReport){
        categoryReportMapper.insert(categoryReport);
    }

    /**
     * 根据ID查询CategoryReport
     * @param id
     * @return
     */
    @Override
    public CategoryReport findById(Date id){
        return  categoryReportMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询CategoryReport全部数据
     * @return
     */
    @Override
    public List<CategoryReport> findAll() {
        return categoryReportMapper.selectAll();
    }
}
