package com.szinternet.crm.databean;

import java.util.List;

public class RepaymentPeriodsBean {

    /**
     * data : [{"times":"6","money":"15.00","pay_order":"测试","pay_time":"2018-01-09 00:01:06"},{"times":"7","money":"16.00","pay_order":"测试","pay_time":"2018-01-09 00:01:07"},{"times":"8","money":"17.00","pay_order":"测试","pay_time":"2018-01-09 00:01:08"},{"times":"9","money":"18.00","pay_order":"测试","pay_time":"2018-01-09 00:01:09"}]
     */
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * times : 6
         * money : 15.00
         * pay_order : 测试
         * pay_time : 2018-01-09 00:01:06
         */
        public String times;
        public String money;
        public String pay_order;
        public String pay_time;
    }
}
