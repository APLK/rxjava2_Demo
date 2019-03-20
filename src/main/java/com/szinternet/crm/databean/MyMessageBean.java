package com.szinternet.crm.databean;

public class MyMessageBean {

    /**
     * tuiguang : {"money":"10.00","create_time":"2009-02-15","username":"15871977763"}
     * sys : {"create_time":"2018-01-03","contents":"内容2","id":"2","titles":"测试2"}
     * fenrun : {"money":"5.00","create_time":"2009-02-15","gain_type":"收款分润","username":"15871977760"}
     */
    public TuiguangEntity tuiguang;
    public SysEntity sys;
    public FenrunEntity fenrun;

    public static class TuiguangEntity {
        /**
         * money : 10.00
         * create_time : 2009-02-15
         * username : 15871977763
         */
        public String money;
        public String create_time;
        public String username;
    }

    public static class SysEntity {
        /**
         * create_time : 2018-01-03
         * contents : 内容2
         * id : 2
         * titles : 测试2
         */
        public String create_time;
        public String contents;
        public String ids;
        public String titles;
    }

    public static class FenrunEntity {
        /**
         * money : 5.00
         * create_time : 2009-02-15
         * gain_type : 收款分润
         * username : 15871977760
         */
        public String money;
        public String create_time;
        public String gain_type;
        public String username;
    }
}
