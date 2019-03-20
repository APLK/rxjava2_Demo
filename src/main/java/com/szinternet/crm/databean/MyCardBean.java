package com.szinternet.crm.databean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MyCardBean implements Serializable {

    /**
     * rc : [{"bank_name":"建设银行","nub":"************1907","id":"1"},{"bank_name":"建设银行","nub":"************1906","id":"2"}]
     * chuxu : {"bank_name":"招商银行","account":"************9133"}
     */
    public List<RcEntity> rc = new ArrayList<>();
    public ChuxuEntity chuxu;

    public static class RcEntity {
        /**
         * bank_name : 建设银行
         * nub : ************1907
         * id : 1
         */
        public String bank_name;
        public String nub;
        public String ids;

        public RcEntity(String bank_name, String nub, String ids) {
            this.bank_name = bank_name;
            this.nub = nub;
            this.ids = ids;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getNub() {
            return nub;
        }

        public void setNub(String nub) {
            this.nub = nub;
        }

        public String getID() {
            return ids;
        }

        public void setID(String ID) {
            this.ids = ids;
        }
    }

    public static class ChuxuEntity {
        /**
         * bank_name : 招商银行
         * account : ************9133
         */
        public String bank_name;
        public String account;

        public ChuxuEntity(String bank_name, String account) {
            this.bank_name = bank_name;
            this.account = account;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
