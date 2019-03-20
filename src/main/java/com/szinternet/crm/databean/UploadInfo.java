/**
 * 文  件   名：CoverInfo.java
 * 包含类名：CoverInfo
 * 版          本：V1.0
 * 创建日期：2014-11-04
 * Copyright(c)2013,深圳证券交易所 All rights reserved
 */
package com.szinternet.crm.databean;

import android.os.Parcel;
import android.os.Parcelable;

import com.szinternet.crm.base.BaseBean;


public class UploadInfo extends BaseBean implements Parcelable {

    public String img_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img_url);
    }

    protected UploadInfo(Parcel in) {
        this.img_url = in.readString();
    }

    public static final Creator<UploadInfo> CREATOR = new Creator<UploadInfo>() {
        @Override
        public UploadInfo createFromParcel(Parcel source) {
            return new UploadInfo(source);
        }

        @Override
        public UploadInfo[] newArray(int size) {
            return new UploadInfo[size];
        }
    };
}
