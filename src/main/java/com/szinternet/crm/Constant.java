package com.szinternet.crm;


import android.os.Environment;

import java.io.File;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class Constant {

    /*
    * DES加解密秘钥
     **/
    public static final String DES_KEY = "CREDITCARD%OE&2017*";

    /**
     * 崩溃信息和缓存存放目录
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/company";


}
