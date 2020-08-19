package com.changgou.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * @ClassName CanalDataEventListener
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/8/19 0019
 * @Version V1.0
 **/
@CanalEventListener
public class CanalDataEventListener {


    /**
     * 监听新增操作
     * @param eventType 当前操作类型
     * @param rowData 发生变更的一行数据
     */
    @InsertListenPoint
    public void  onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        rowData.getAfterColumnsList().forEach(column -> {
            System.out.println("onEventInsert--"+column.getName()+"::"+column.getValue());
        });
    }

    /***
     * 修改数据监听
     * @param rowData
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.RowData rowData) {
        System.out.println("UpdateListenPoint");
        rowData.getAfterColumnsList().forEach((c) -> System.out.println("By--Annotation: " + c.getName() + " ::   " + c.getValue()));
    }

    /***
     * 删除数据监听
     * @param eventType
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType) {
        System.out.println("DeleteListenPoint");
    }

    /**
     * 自定义数据修改监听
     * @param eventType
     * @param rowData
     */
    @ListenPoint(
            destination = "example",
            schema = "changgou_content",
            table = {"tb_content_category"},
            eventType = CanalEntry.EventType.UPDATE

    )
    public void onEventCustomUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        System.err.println("DeleteListenPoint");
        rowData.getAfterColumnsList().forEach((c) -> System.out.println("By--Annotation: " + c.getName() + " ::   " + c.getValue()));
    }

}
