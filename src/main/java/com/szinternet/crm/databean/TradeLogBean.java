package com.szinternet.crm.databean;

import java.util.List;

public class TradeLogBean {

    /**
     * data : [{"money":"10000.00","create_time":"2018-01-18","pay_order":"JX201801182226571802","fee":"48.00","out_status":"0","bank_name":"建设银行","id":"4","in_status":"0"},{"money":"10000.00","create_time":"2018-01-18","pay_order":"0","fee":"48.00","out_status":"0","bank_name":"建设银行","id":"3","in_status":"0"},{"money":"10000.00","create_time":"1970-01-01","pay_order":"CS123456","fee":"52.00","out_status":"0","bank_name":"建设银行","id":"2","in_status":"0"},{"money":"1000.00","create_time":"1970-01-01","pay_order":"CS123456","fee":"0.00","out_status":"0","bank_name":"建设银行","id":"1","in_status":"0"}]
     */
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * money : 10000.00
         * create_time : 2018-01-18
         * pay_order : JX201801182226571802
         * fee : 48.00
         * out_status : 0
         * bank_name : 建设银行
         * id : 4
         * in_status : 0
         */
        public String money;
        public String create_time;
        public String pay_order;
        public String fee;
        public String out_status;
        public String bank_name;
        public String id;
        public String in_status;
    }
}
