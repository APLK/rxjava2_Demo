package com.szinternet.crm.databean;

import java.io.Serializable;
import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SpendRecordBean {


    /**
     * data : [{"pay_mub":"22","s_time":"2018.01.14","ids":"3","pay_fee":"19.00","nub":"1906","pay_money":"1000.00","type":"1","rc_id":"2","e_time":"2018.01.19","username":"15871977750"},{"pay_mub":"22","s_time":"2018.01.04","ids":"2","pay_fee":"19.00","nub":"1906","pay_money":"1000.00","type":"1","rc_id":"2","e_time":"2018.01.09","username":"15871977750"},{"pay_mub":"22","s_time":"2018.01.04","ids":"1","pay_fee":"19.00","nub":"1907","pay_money":"1000.00","type":"1","rc_id":"1","e_time":"2018.01.09","username":"15871977750"}]
     * page : 1
     */
    public List<DataEntity> data;
    public int page;
    private double money;
    private int count;
    private int type;
    private String code;

    public static class DataEntity implements Serializable {
        /**
         * pay_mub : 22
         * s_time : 2018.01.14
         * ids : 3
         * rc_id : 2
         * pay_fee : 19.00
         * nub : 1906
         * pay_money : 1000.00
         * type : 1
         * e_time : 2018.01.19
         * username : 15871977750
         */
        public String pay_mub;
        public String s_time;
        public String ids;
        public String pay_fee;
        public String nub;
        public String pay_money;
        public String type;
        public String rc_id;
        public String e_time;
        public String username;
        public String bank_name;
    }
}
