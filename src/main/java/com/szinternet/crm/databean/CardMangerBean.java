package com.szinternet.crm.databean;

import java.io.Serializable;


public class CardMangerBean implements Serializable {
    /**
     * 信用卡及银行卡
     * bank_name : 建设银行
     * account : ************9133
     * id : 1
     * type : 1  //0代表储蓄卡,1代表信用卡
     */
    public String bank_name;
    public String ids;
    public String account;
    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public CardMangerBean(int type, String bank_name, String account, String ids) {
        this.type = type;
        this.bank_name = bank_name;
        this.ids = ids;
        this.account = account;
    }


    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }


    public String getID() {
        return ids;
    }

    public void setID(String ids) {
        this.ids = ids;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
