package com.szinternet.crm.databean;


public class CreditCollectInfoBean {

    /**
     * rc_bank_id : 1
     * rc_bank_name : 建设银行
     * nub : 1907
     * user_bank_id : 1
     * bank_name : 招商银行
     * account : 9133
     * rate : 0.50+2.00元
     */

    private String rc_bank_id;
    private String rc_bank_name;
    private String nub;
    private String user_bank_id;
    private String bank_name;
    private String account;
    private String rate;
    private String grade;
    private String up_rate;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getUp_rate() {
        return up_rate;
    }

    public void setUp_rate(String up_rate) {
        this.up_rate = up_rate;
    }

    public String getRc_bank_id() {
        return rc_bank_id;
    }

    public void setRc_bank_id(String rc_bank_id) {
        this.rc_bank_id = rc_bank_id;
    }

    public String getRc_bank_name() {
        return rc_bank_name;
    }

    public void setRc_bank_name(String rc_bank_name) {
        this.rc_bank_name = rc_bank_name;
    }

    public String getNub() {
        return nub;
    }

    public void setNub(String nub) {
        this.nub = nub;
    }

    public String getUser_bank_id() {
        return user_bank_id;
    }

    public void setUser_bank_id(String user_bank_id) {
        this.user_bank_id = user_bank_id;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
