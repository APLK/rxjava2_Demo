package com.szinternet.crm.databean;

import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CashRecordBean {


    /**
     * data : [{"create_time":"2018-01-07 10:01:36","money":"10.00"},{"create_time":"2018-01-07 10:01:36","money":"10.00"},{"create_time":"2018-01-07 10:01:36","money":"10.00"},{"create_time":"2018-01-07 10:01:36","money":"10.00"},{"create_time":"2018-01-07 10:01:36","money":"10.00"}]
     * page : 1
     */
    public List<DataEntity> data;
    public int page;

    public static class DataEntity {
        /**
         * create_time : 2018-01-07 10:01:36
         * money : 10.00
         */
        public String money;
        public String amount;
        public String create_time;
        public String order_number;
        public String state;
    }
}
