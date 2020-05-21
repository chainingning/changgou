package com.changgou.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileUploadController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/5/21 0021
 * @Version V1.0
 **/
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileUploadController {

    /**
     * 文件上传
     * @return
     */
    @PostMapping
    public Result upload(@RequestParam(value = "file")MultipartFile file)throws Exception{
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),//文件名
                file.getBytes(),//文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename())//获取文件扩展名
        );


        //调用FastDFSUtil工具类，将文件传入FastDFS中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);
        //拼接访问地址 url:http://192.168.255.222:8080/group1/M00/00/00/wKjThF0DBzaAP23MAAXz2mMp9oM26.jpeg
        String fileUrl = "http://192.168.255.222:8080/"+uploads[0]+"/"+uploads[1];
        return new Result(true, StatusCode.OK,"上传成功",fileUrl);
    }

}
