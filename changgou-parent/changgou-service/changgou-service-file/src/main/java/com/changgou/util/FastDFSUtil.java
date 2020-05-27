package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public static String[] upload(FastDFSFile file) throws Exception {
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",file.getAuthor());

        //获取trackerServer
        TrackerServer trackerServer = getTrackerServer();
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

    /**
     * 获取文件信息
     * @param groupName 文件组名
     * @param remoteFileName 文件的存储路径名字
     */
    public static FileInfo getFile(String groupName, String remoteFileName){
        try {
            //获取trackerServer
            TrackerServer trackerServer = getTrackerServer();
            //通过TrackerServer获取StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //获取文件信息
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件信息
     * @param groupName 文件组名
     * @param remoteFileName 文件的存储路径名字
     */
    public static InputStream downloadFile(String groupName, String remoteFileName){
        try {
            //获取trackerServer
            TrackerServer trackerServer = getTrackerServer();
            //通过TrackerServer获取StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //获取文件信息
            byte[] buffer = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件信息
     * @param groupName 文件组名
     * @param remoteFileName 文件的存储路径名字
     */
    public void delete(String groupName, String remoteFileName) throws Exception{
        //获取trackerServer
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer获取StorageClient对象
        StorageClient storageClient = new StorageClient(trackerServer, null);

        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取storage信息
     * @return
     * @throws Exception
     */
    public static StorageServer getStorage()throws Exception{
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获得TrackerServer信息
        TrackerServer trackerServer =trackerClient.getConnection();
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 根据文件组名和文件存储路径获取Storage服务的IP、端口信息
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public ServerInfo[] getServerInfo(String groupName, String remoteFileName)throws Exception{
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获得TrackerServer信息
        TrackerServer trackerServer =trackerClient.getConnection();

        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    /***
     * 获取TrackerServer
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /***
     * 获取StorageClient
     * @return
     * @throws Exception
     */
    public static StorageClient getStorageClient() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }


    /***
     * 获取Tracker服务地址
     */
    public static String getTrackerUrl(){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Tracker地址
            return "http://"+trackerServer.getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        FileInfo file = getFile("group1","");
        System.out.println(file.getSourceIpAddr());
        System.out.println(file.getFileSize());
    }

}
