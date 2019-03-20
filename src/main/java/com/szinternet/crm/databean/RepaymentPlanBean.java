package com.szinternet.crm.databean;

import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentPlanBean {
    public List<RepaymentPlanBean.DataEntity> data;

    public static class DataEntity {
        public int type;
        public int times;
        public String money;
        public String create_time;

        public DataEntity(int type, int times, String money, String create_time) {
            this.type = type;
            this.times = times;
            this.money = money;
            this.create_time = create_time;
        }
    }

}
