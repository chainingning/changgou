package com.changgou.goods.dao;
import com.changgou.goods.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:Category的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface CategoryMapper extends Mapper<Category> {

    //("SELECT tb.* FROM tb_brand tb,tb_category_brand tcb WHERE tb.id=tcb.brand_id AND tcb.category_id=#{pid}")

    @Select("SELECT * FROM tb_category WHERE parent_id=#{pid}")
    List<Category> findByParentId(Integer pid);
}
