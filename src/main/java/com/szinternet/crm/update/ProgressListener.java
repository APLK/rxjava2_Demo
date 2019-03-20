package com.szinternet.crm.update;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface ProgressListener {

    /**
     * @param section     已经下载或上传字节数
     * @param total        总字节数部分
     * @param done         是否完成
     */
    void onProgress(float section, float total, boolean done);

}
