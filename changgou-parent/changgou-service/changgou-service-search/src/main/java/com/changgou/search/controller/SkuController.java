package com.changgou.search.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName SkuController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/8/27 0027
 * @Version V1.0
 **/

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SkuController {
    @Autowired
    private SkuService skuService;


    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @PostMapping
    public Map search(@RequestBody(required = false) Map searchMap){
        return  skuService.search(searchMap);
    }

    /**
     * 导入数据
     * @return
     */
    @GetMapping("/import")
    public Result importData(){
        skuService.importData();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！");
    }
}
