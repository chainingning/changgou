package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @ClassName FastDFSUtil
 * @Description:
 * 实现FastDFS文件管理
 * 文件上传
 * 文件删除
 * 文件下载
 * 文件信息获取
 * Storage信息获取
 * Tracker信息获取
 * @Author ning.chai@foxmail.com
 * @Date 2020/5/20 0020
 * @Version V1.0
 **/
public class FastDFSUtil {
    /**
     * 初始化tracker信息
     */
    static {
        try {
            //获取tracker的配置文件fdfs_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            //加载tracker配置信息
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 文件上传
     * @param file : 要上传的文件信息封装->FastDFSFile
     * @return String[]
     *          1:文件上传所存储的组名
     *          2:文件存储路径
     */
    public static String[] upload(FastDFSFile file) throws IOException, MyException {
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",file.getAuthor());

        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过Tracker访问TrackerServer服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer的链接信息可以获取Storage的连接信息，创建StorageClient对象存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        /**
         * 通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
         * 1:上传文件的字节数组
         * 2:文件的扩展名 jpg
         * 3:附加参数 比如：拍摄地址-无锡
         *
         * uploads[]
         *  uploads[0]:文件上传所存储的storage的组名字 group1
         *  uploads[1]:文件存储到Storage上的文件路径 M00/02/44/xxx.jgp
         *
         */
        String[] uploads = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        return uploads;

    }

}
