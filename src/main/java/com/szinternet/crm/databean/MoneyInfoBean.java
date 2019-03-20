package com.szinternet.crm.databean;

import java.util.ArrayList;
import java.util.List;

public class MoneyInfoBean {
    public List<Float> getSpendlist() {
        return spendlist;
    }

    public void setSpendlist(List<Float> spendlist) {
        this.spendlist = spendlist;
    }

    public List<Integer> getRepaymentList() {
        return repaymentList;
    }

    public void setRepaymentList(List<Integer> repaymentList) {
        this.repaymentList = repaymentList;
    }

    List<Float> spendlist = new ArrayList<Float>();
    List<Integer> repaymentList = new ArrayList<Integer>();

    public MoneyInfoBean(List<Integer> repaymentList,List<Float> spendlist) {
        this.spendlist = spendlist;
        this.repaymentList = repaymentList;
    }
}
