/**
 * 文  件   名：FileUtil.java
 * 包含类名：FileUtil
 * 版          本：V1.0
 * 创建日期：2014-11-04
 * Copyright(c)2013,深圳证券交易所 All rights reserved
 */
package com.szinternet.crm.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;

import static com.szinternet.crm.Constant.DES_KEY;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class FileUtil {

    /**
     * 图片目录
     */
    private static final String CACHE_IMG_DIR_NAME = "image";
    /**
     * 下载文件目录
     */
    private static final String DOWNLOAD_FILE_DIR_NAME = "download";

    public static String getCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return context.getExternalCacheDir() != null ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        }
        return context.getCacheDir().getPath();
    }

    /**
     * 获取应用缓存目录下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getCacheFile(Context context, String fileName) {
        return new File(getCachePath(context) + File.separator + fileName);
    }

    /**
     * 保存文件内容
     *
     * @param context
     * @param fileName
     */
    public static void saveCache(Context context, String fileName, Object obj, String path) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                fileName = DESUtils.encode(fileName.toUpperCase(), DES_KEY, "UTF-8");

                Gson gson = new Gson();
                String str = gson.toJson(obj);

                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdir();
                FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                fos.write(str.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取文件内容
     *
     * @param fileName 文件名称
     * @return 序列化对象
     */
    public static Object readCacheData(Context context, String fileName, Type type, String path) {  // DATA_CACHE_PATH
        try {
            fileName = DESUtils.encode(fileName.toUpperCase(), DES_KEY, "UTF-8");
            String dirPath = path + File.separator + fileName;
            File file = new File(dirPath);
            if (!file.exists()) {
                return null;
            }

            String str = file2String(file, "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(str, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context
     * @param folderName
     * @param fileName
     * @return
     */
    public static File getCacheFile(Context context, String folderName, String fileName) {
        String dirPath = getCachePath(context) + File.separator + folderName;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return new File(dirPath + File.separator + fileName);
    }

    /**
     * 获取下载的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getDownloadFile(Context context, String fileName) {
        return getCacheFile(context, DOWNLOAD_FILE_DIR_NAME, fileName);
    }

    /**
     * 获取缓存图片文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getImgCacheFile(Context context, String fileName) {
        LogUtils.i("1", "getExternalStorageState=" + getCacheFile(context, CACHE_IMG_DIR_NAME, fileName));
        return getCacheFile(context, CACHE_IMG_DIR_NAME, fileName);
    }

    /**
     * 根据文件路径获取文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        String resultString = filePath;

        if (!TextUtils.isEmpty(filePath)) {
            int index = filePath.lastIndexOf(File.separatorChar);
            if (index != -1) {
                resultString = filePath.substring(index + 1);
            }
        }
        return resultString;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffixName(String fileName) {
        String suffixName = "";
        if (!TextUtils.isEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                suffixName = fileName.substring(index);
            }
        }
        return suffixName;
    }

    /**
     * 获取文件的目录路径
     *
     * @param filePath
     * @return
     */
    public static String getFileDirs(String filePath) {
        String resultString = filePath;

        if (!TextUtils.isEmpty(filePath)) {
            int index = filePath.lastIndexOf(File.separatorChar);
            if (index != -1) {
                resultString = filePath.substring(0, index);
            }
        }
        return resultString;
    }

    /**
     * 重命名文件
     *
     * @param oldPath
     * @return
     */
    public static boolean rename(String oldPath, String newFileName) {
        if (!TextUtils.isEmpty(oldPath) && !TextUtils.isEmpty(newFileName)) {
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                String dirs = getFileDirs(oldPath);
                File newFile = new File(dirs + File.separator + newFileName);
                return oldFile.renameTo(newFile);
            }
        }
        return false;
    }

    /**
     * 重命名文件
     *
     * @return
     */
    public static boolean rename(String dirs, String fileName, String newFileName) {
        if (!TextUtils.isEmpty(dirs) && !TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(newFileName)) {
            return rename(dirs + File.separator + fileName, newFileName);
        }
        return false;
    }

    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param file     文本文件
     * @param encoding 编码类型
     * @return 转换后的字符串
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (!TextUtils.isEmpty(encoding)) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            // 将输入流写入输出流
            char[] buffer = new char[100 * 1024];
            int n;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return writer.toString();
    }

    /**
     * 文件转化为字节数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                return null;
            }
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream(4096);
                byte[] b = new byte[4096];
                int n;
                while ((n = in.read(b)) != -1) {
                    out.write(b, 0, n);
                }
                in.close();
                out.close();
                ret = out.toByteArray();
            } catch (IOException e) {
                LogUtils.e("FileUtil", "get bytes from file process error!");
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            LogUtils.e("FileUtil", "get bytes from file process error!");
        }
        return ret;
    }

    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFile
     * @return
     */
    public static File newFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

}
