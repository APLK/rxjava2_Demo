package com.szinternet.crm.databean;

import java.io.Serializable;
import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MyGradeBean {

    /**
     * myGrade : VIP
     * upClass : [{"repay_rate":"0.50","collection_rate":"0.38","grade":"2","grade_name":"市级代理","up_money":"188.00"},{"repay_rate":"0.46","collection_rate":"0.30","grade":"3","grade_name":"省级代理","up_money":"980.00"},{"repay_rate":"0.43","collection_rate":"0.28","grade":"4","grade_name":"合伙人","up_money":"2980.00"}]
     */
    public String myGrade;
    public List<UpClassEntity> upClass;

    public static class UpClassEntity implements Serializable {
        /**
         * repay_rate : 0.50
         * collection_rate : 0.38
         * grade : 2
         * grade_name : 市级代理
         * up_money : 188.00
         */
        public String repay_rate;
        public String collection_rate;
        public String grade;
        public String grade_name;
        public String up_money;
        public List<InfoEntity> info;

        public UpClassEntity(String repay_rate, String collection_rate, String grade, String grade_name, String up_money) {
            this.repay_rate = repay_rate;
            this.collection_rate = collection_rate;
            this.grade = grade;
            this.grade_name = grade_name;
            this.up_money = up_money;
        }

        public String getRepay_rate() {
            return repay_rate;
        }

        public String getCollection_rate() {
            return collection_rate;
        }

        public String getGrade() {
            return grade;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public String getUp_money() {
            return up_money;
        }
    }

    public static class InfoEntity implements Serializable {
        public String msg;
    }
}
