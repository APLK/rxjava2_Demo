package com.szinternet.crm.databean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AllBankBean implements Parcelable {

    /**
     * data : [{"bank_name":"招商银行","id":"1"},{"bank_name":"中国工商银行","id":"2"},{"bank_name":"中国农业银行","id":"3"},{"bank_name":"中国银行","id":"4"},{"bank_name":"中国建设银行","id":"5"},{"bank_name":"交通银行","id":"6"},{"bank_name":"中信银行","id":"7"},{"bank_name":"中国光大银行","id":"8"},{"bank_name":"中国邮政储蓄银行","id":"9"},{"bank_name":"平安银行","id":"10"},{"bank_name":"浦发银行","id":"11"},{"bank_name":"中国民生银行","id":"12"},{"bank_name":"兴业银行","id":"13"},{"bank_name":"华夏银行","id":"14"},{"bank_name":"徽商银行","id":"15"}]
     */
    public List<DataEntity> data;

    protected AllBankBean(Parcel in) {
    }

    public static final Creator<AllBankBean> CREATOR = new Creator<AllBankBean>() {
        @Override
        public AllBankBean createFromParcel(Parcel in) {
            return new AllBankBean(in);
        }

        @Override
        public AllBankBean[] newArray(int size) {
            return new AllBankBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class DataEntity implements Parcelable {
        /**
         * bank_name : 招商银行
         * id : 1
         */
        public String bank_name;
        public String ids;
        public String nub;

        public DataEntity(String bank_name, String ids) {
            this.bank_name = bank_name;
            this.ids = ids;
        }

        protected DataEntity(Parcel in) {
            bank_name = in.readString();
            ids = in.readString();
            nub = in.readString();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bank_name);
            dest.writeString(ids);
            dest.writeString(nub);
        }
    }
}
