package com.changgou.search.service;

import java.util.Map;

/**
 * @ClassName SkuService
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/8/27 0027
 * @Version V1.0
 **/
public interface SkuService {

    /**
     * 条件搜索
     * @param searchMap
     * @return Map
     */
    Map<String,Object> search(Map<String,Object> searchMap);

    /**
     * 导入索引库
     */
    void importData();
}
