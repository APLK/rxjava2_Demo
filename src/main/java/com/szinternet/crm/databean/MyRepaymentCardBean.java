package com.szinternet.crm.databean;

import java.io.Serializable;
import java.util.List;

public class MyRepaymentCardBean {

    /**
     * data : [{"limit_money":"70000","end_remoney":"1","end_time":"924","ids":"1","nub":"6222285503101907","cvn":"214","user":"吴远辉","bill_day":"20"},{"limit_money":"70000","end_remoney":"1","end_time":"924","ids":"2","nub":"6222285503101906","cvn":"214","user":"吴远辉","bill_day":"20"},{"limit_money":"70000","end_remoney":"1","end_time":"924","ids":"3","nub":"6222285503101907","cvn":"214","user":"吴远辉","bill_day":"20"}]
     */
    public List<DataEntity> data;

    public static class DataEntity implements Serializable {
        /**
         * limit_money : 70000
         * end_remoney : 1
         * end_time : 924
         * ids : 1
         * nub : 6222285503101907
         * cvn : 214
         * user : 吴远辉
         * bill_day : 20
         */
        public String limit_money;
        public String end_remoney;
        public String end_time;
        public String ids;
        public String nub;
        public String cvn;
        public String user;
        public String bill_day;
        public String bank_name;
        public int status;
    }
}
