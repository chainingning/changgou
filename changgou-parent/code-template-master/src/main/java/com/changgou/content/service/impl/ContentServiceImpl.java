package com.changgou.content.service.impl;
import com.changgou.content.dao.ContentMapper;
import com.changgou.content.pojo.Content;
import com.changgou.content.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:chaining
 * @Description:Content业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;


    /**
     * Content条件+分页查询
     * @param content 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Content> findPage(Content content, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(content);
        //执行搜索
        return new PageInfo<Content>(contentMapper.selectByExample(example));
    }

    /**
     * Content分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Content> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Content>(contentMapper.selectAll());
    }

    /**
     * Content条件查询
     * @param content
     * @return
     */
    @Override
    public List<Content> findList(Content content){
        //构建查询条件
        Example example = createExample(content);
        //根据构建的条件查询数据
        return contentMapper.selectByExample(example);
    }


    /**
     * Content构建查询对象
     * @param content
     * @return
     */
    public Example createExample(Content content){
        Example example=new Example(Content.class);
        Example.Criteria criteria = example.createCriteria();
        if(content!=null){
            // 
            if(!StringUtils.isEmpty(content.getId())){
                    criteria.andEqualTo("id",content.getId());
            }
            // 内容类目ID
            if(!StringUtils.isEmpty(content.getCategoryId())){
                    criteria.andEqualTo("categoryId",content.getCategoryId());
            }
            // 内容标题
            if(!StringUtils.isEmpty(content.getTitle())){
                    criteria.andLike("title","%"+content.getTitle()+"%");
            }
            // 链接
            if(!StringUtils.isEmpty(content.getUrl())){
                    criteria.andEqualTo("url",content.getUrl());
            }
            // 图片绝对路径
            if(!StringUtils.isEmpty(content.getPic())){
                    criteria.andEqualTo("pic",content.getPic());
            }
            // 状态,0无效，1有效
            if(!StringUtils.isEmpty(content.getStatus())){
                    criteria.andEqualTo("status",content.getStatus());
            }
            // 排序
            if(!StringUtils.isEmpty(content.getSortOrder())){
                    criteria.andEqualTo("sortOrder",content.getSortOrder());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        contentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Content
     * @param content
     */
    @Override
    public void update(Content content){
        contentMapper.updateByPrimaryKey(content);
    }

    /**
     * 增加Content
     * @param content
     */
    @Override
    public void add(Content content){
        contentMapper.insert(content);
    }

    /**
     * 根据ID查询Content
     * @param id
     * @return
     */
    @Override
    public Content findById(Long id){
        return  contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Content全部数据
     * @return
     */
    @Override
    public List<Content> findAll() {
        return contentMapper.selectAll();
    }
}
