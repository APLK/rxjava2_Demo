package com.szinternet.crm.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class DESUtils {

    private static String Algorithm = "DES/CBC/PKCS5Padding"; // 定义 加密算法,可用

    // 生成密钥, 注意此步骤时间比较长
    public static byte[] getKey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
        SecretKey deskey = keygen.generateKey();
        return deskey.getEncoded();
    }

    public static String encode(String source, String key) {
        try {
            return byte2hex(encode(source.getBytes("GBK"), key));
        } catch (Exception e) {
            return "";
        }
    }

    public static String encode(String source, String key, String charset) {
        try {
            if (charset == null || "".equals(charset.trim())) {
                return encode(source, key);
            } else {
                return byte2hex(encode(source.getBytes(charset), key));
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String decode(String res, String key) {
        try {
            return new String(decode(hex2byte(res), key), "GBK");
        } catch (Exception e) {
            return "";
        }
    }

    public static String decode(String res, String key, String charset) {
        try {
            if (charset == null || "".equals(charset.trim())) {
                return decode(res, key);
            } else {
                return new String(decode(hex2byte(res), key), charset);
            }
        } catch (Exception e) {
            return "";
        }
    }

    // 加密
    public static byte[] encode(byte[] input, String key) throws Exception {

        DESKeySpec dks = new DESKeySpec(key.getBytes("GBK"));
        IvParameterSpec iv = new IvParameterSpec(key.substring(0, 8).getBytes("GBK"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key1 = keyFactory.generateSecret(dks);

        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, key1, iv);
        byte[] cipherByte = c1.doFinal(input);
        return cipherByte;
    }

    // 解密
    public static byte[] decode(byte[] input, String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes("GBK"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

        IvParameterSpec iv = new IvParameterSpec(key.substring(0, 8).getBytes("GBK"));
        SecretKey key1 = keyFactory.generateSecret(dks);

        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, key1, iv);

        byte[] clearByte = c1.doFinal(input);
        return clearByte;
    }

    // 字节码转换成16进制字符串
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        StringBuilder stmp;
        for (int i = 0; i < b.length; i++) {
            stmp = new StringBuilder(Integer.toHexString(b[i] & 0xFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp.toString());
            }
        }
        return hs.toString().toUpperCase();
    }

    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

	/*public static void main(String[] args) throws Exception {
         //debug = true;
		String key = "ZqBGame%HDY&2014*";
		
		String str_In = "{\"userNames\":[\"test100@szse.cn\"],\"begin\":\"\",\"end\":\"2014-07-17 09:02:03\"}";
		String in  = encode(str_In, key);
		System.out.println("值："+in);
		
		str_In = "123GGGaaa哦哦哦！！！";
		in  = encode(str_In, key);
		System.out.println("值："+in);
		
		String str_Out="F739E340C9207454989DACC0C2C87726B85F26CDA6284A97CE4C1F770D0ED8545EB7059111931898F80C80A81103ABB65231B85FCB8B3E3317A37969508332B91B0D288347914B968F48F13ED23FE9EC970587E30D206BA67EB252047544183A";
		String out=decode(str_Out,key);
		System.out.println("值："+out);
		
		str_Out="32C91595C819CE23B394A8F8003B8441EABBB37B520A81E1";
		out=decode(str_Out,key);
		System.out.println("值："+out);
	}*/

}

