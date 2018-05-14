package com.jzg.framework.utils.test;

import com.jzg.framework.utils.encrypt.RsaEncrypt;
import com.jzg.framework.utils.encrypt.ThreeDES;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RsaEncryptTest {

    public static final String PUBLIC_KEY_PATH = getPath() + "pk";
    public static final String PRIVATE_KEY_PATH = getPath() + "sk";

    private static String getPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    @Test
    public void test1(){
        System.out.println("admin:" + ThreeDES.encrypt("admin"));
        System.out.println("test:" + ThreeDES.encrypt("test"));
        System.out.println("123456:" + ThreeDES.encrypt("123456"));
    }

    @Test
    public void test() {
        try {
            createKey(600);
            String str = "My chinese name is Mr.Z!王";
            System.out.println("原始文本：" + str);
            RsaEncrypt rsa = new RsaEncrypt();

            RSAPrivateKey privateKey = (RSAPrivateKey) rsa.readFromFile(PRIVATE_KEY_PATH);

            RSAPublicKey publickKey = (RSAPublicKey) rsa.readFromFile(PUBLIC_KEY_PATH);
            byte[] encbyte = rsa.encrypt(str, privateKey);
            System.out.println("私钥加密：");
            String encStr = rsa.toHexString(encbyte);
            System.out.println(encStr);

            byte[] signBytes = rsa.sign(str, privateKey);
            System.out.println("私钥签名：");
            String signStr = rsa.toHexString(signBytes);
            System.out.println(signStr);

            System.out.println("公钥验证签名：");
            if (rsa.verifySign(str, signStr, publickKey)) {
                System.out.println("RSA sign check success");
            } else {
                System.out.println("RSA sign check failure");
            }
            byte[] decByte = rsa.decrypt(encStr, publickKey);
            System.out.println("公钥解密：");
            String decStr = new String(decByte);
            System.out.println(decStr);
        } catch (Exception e) {

        }
    }

    private void createKey(int in) {
        KeyPairGenerator kpg = null;
        KeyPair kp = null;
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(in);
            kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
            fileOut = new FileOutputStream(PUBLIC_KEY_PATH);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(publicKey);
            fileOut = new FileOutputStream(PRIVATE_KEY_PATH);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(privateKey);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
