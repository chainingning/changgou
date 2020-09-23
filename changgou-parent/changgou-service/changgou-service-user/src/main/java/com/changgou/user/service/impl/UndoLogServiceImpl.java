package com.changgou.user.service.impl;

import com.changgou.user.dao.UndoLogMapper;
import com.changgou.user.pojo.UndoLog;
import com.changgou.user.service.UndoLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:admin
 * @Description:UndoLog业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
    private UndoLogMapper undoLogMapper;


    /**
     * UndoLog条件+分页查询
     * @param undoLog 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<UndoLog> findPage(UndoLog undoLog, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(undoLog);
        //执行搜索
        return new PageInfo<UndoLog>(undoLogMapper.selectByExample(example));
    }

    /**
     * UndoLog分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<UndoLog> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<UndoLog>(undoLogMapper.selectAll());
    }

    /**
     * UndoLog条件查询
     * @param undoLog
     * @return
     */
    @Override
    public List<UndoLog> findList(UndoLog undoLog){
        //构建查询条件
        Example example = createExample(undoLog);
        //根据构建的条件查询数据
        return undoLogMapper.selectByExample(example);
    }


    /**
     * UndoLog构建查询对象
     * @param undoLog
     * @return
     */
    public Example createExample(UndoLog undoLog){
        Example example=new Example(UndoLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(undoLog!=null){
            // 
            if(!StringUtils.isEmpty(undoLog.getId())){
                    criteria.andEqualTo("id",undoLog.getId());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getBranchId())){
                    criteria.andEqualTo("branchId",undoLog.getBranchId());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getXid())){
                    criteria.andEqualTo("xid",undoLog.getXid());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getRollbackInfo())){
                    criteria.andEqualTo("rollbackInfo",undoLog.getRollbackInfo());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogStatus())){
                    criteria.andEqualTo("logStatus",undoLog.getLogStatus());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogCreated())){
                    criteria.andEqualTo("logCreated",undoLog.getLogCreated());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getLogModified())){
                    criteria.andEqualTo("logModified",undoLog.getLogModified());
            }
            // 
            if(!StringUtils.isEmpty(undoLog.getExt())){
                    criteria.andEqualTo("ext",undoLog.getExt());
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
        undoLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UndoLog
     * @param undoLog
     */
    @Override
    public void update(UndoLog undoLog){
        undoLogMapper.updateByPrimaryKey(undoLog);
    }

    /**
     * 增加UndoLog
     * @param undoLog
     */
    @Override
    public void add(UndoLog undoLog){
        undoLogMapper.insert(undoLog);
    }

    /**
     * 根据ID查询UndoLog
     * @param id
     * @return
     */
    @Override
    public UndoLog findById(Long id){
        return  undoLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UndoLog全部数据
     * @return
     */
    @Override
    public List<UndoLog> findAll() {
        return undoLogMapper.selectAll();
    }
}
