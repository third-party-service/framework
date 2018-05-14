package com.jzg.framework.utils.encrypt;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;

public final class DesEncrypt {
    private static String ALGORITHM = "DESede";
    private static String KEYFile = getPath() + "dkf";

    public static String getPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    private static void generateKey()
            throws Exception {
        SecureRandom sr = new SecureRandom();

        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);

        kg.init(sr);

        SecretKey key = kg.generateKey();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(KEYFile));

        oos.writeObject(key);

        oos.close();
    }

    public static String encrypt(String source)
            throws Exception {
        File file = new File(KEYFile);
        if (!file.exists()) {
            throw new RuntimeException("Key file not found!");
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(KEYFile));

        SecretKey key = (SecretKey) ois.readObject();

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(1, key);
        byte[] b = source.getBytes();

        byte[] b1 = cipher.doFinal(b);

        return Base64.encodeBase64String(b1);
    }

    public static String decrypt(String cryptograph)
            throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getPath() + KEYFile));

        SecretKey key = (SecretKey) ois.readObject();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(2, key);
        byte[] b1 = Base64.decodeBase64(cryptograph);
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
