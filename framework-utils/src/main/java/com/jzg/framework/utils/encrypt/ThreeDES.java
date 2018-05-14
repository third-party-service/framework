package com.jzg.framework.utils.encrypt;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 历史遗留
 */
public class ThreeDES {

    private static final String k = "8767c010b3fc40fbb2482e395aeb704a";

    /// <summary>
    /// 3des解码
    /// </summary>
    /// <param name="value">待解密字符串</param>
    /// <param name="key">原始密钥字符串</param>
    /// <returns></returns>
    public static String decrypt(String value, String key) throws Exception {
        byte[] b = decryptMode(getKeyBytes(key), Base64.decode(value));
        return new String(b);
    }

    public static String decrypt(String value) {
        try {
            return decrypt(value, k);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /// <summary>
    /// 3des加密
    /// </summary>
    /// <param name="value">待加密字符串</param>
    /// <param name="strKey">原始密钥字符串</param>
    /// <returns></returns>
    public static String encrypt(String value, String key) throws Exception {
        String str = byte2Base64(encryptMode(getKeyBytes(key), value.getBytes()));
        return str;
    }
    /// <summary>
    /// 3des加密
    /// </summary>
    /// <param name="value">待加密字符串</param>
    /// <returns></returns>
    public static String encrypt(String value) {
        String str = null;
        try {
            str = byte2Base64(encryptMode(getKeyBytes(k), value.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }


    //计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
    public static byte[] getKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");

        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());

        byte[] bkey = alg.digest();
        System.out.println("md5key.length=" + bkey.length);
        System.out.println("md5key=" + byte2hex(bkey));

        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }

        for (int i = start; i < 24; i++) {//为了与.net16位key兼容
            bkey24[i] = bkey[i - start];
        }

        System.out.println("byte24key.length=" + bkey24.length);
        System.out.println("byte24key=" + byte2hex(bkey24));
        return bkey24;
    }


    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish


    //keybyte为加密密钥，长度为24字节

    //src为被加密的数据缓冲区（源）

    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }

        return null;
    }


    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);

        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


    //转换成base64编码
    public static String byte2Base64(byte[] b) {
        return Base64.encode(b);
    }


    //转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";

        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;

            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }
}