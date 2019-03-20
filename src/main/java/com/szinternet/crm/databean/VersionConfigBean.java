package com.szinternet.crm.databean;


import com.szinternet.crm.base.BaseBean;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * @des 版本更新
 */
public class VersionConfigBean extends BaseBean {
    public VersionConfigEntity version;

    public static class VersionConfigEntity {
        public int clientType;
        public String versionNo;
        public String url;
        public int isUpdate;
        public String memo;
        public long publishTime;
    }
}
