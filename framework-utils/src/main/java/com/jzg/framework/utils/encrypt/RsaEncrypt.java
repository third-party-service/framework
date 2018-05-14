package com.jzg.framework.utils.encrypt;


import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public final class RsaEncrypt {




    public byte[] encrypt(String message, Key key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, key);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    public byte[] decrypt(String message, Key key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, key);
        return cipher.doFinal(toBytes(message));
    }

    public byte[] sign(String message, PrivateKey key)
            throws Exception {
        Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initSign(key);
        signetcheck.update(message.getBytes("ISO-8859-1"));
        return signetcheck.sign();
    }

    public boolean verifySign(String message, String signStr, PublicKey key)
            throws Exception {
        if ((message == null) || (signStr == null) || (key == null)) {
            return false;
        }
        Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initVerify(key);
        signetcheck.update(message.getBytes("ISO-8859-1"));
        return signetcheck.verify(toBytes(signStr));
    }

    public Object readFromFile(String fileName)
            throws Exception {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));

        Object obj = input.readObject();
        input.close();
        return obj;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEXCHAR[((b[i] & 0xF0) >>> 4)]);
            sb.append(HEXCHAR[(b[i] & 0xF)]);
        }
        return sb.toString();
    }

    public static final byte[] toBytes(String s) {
        byte[] bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = ((byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16));
        }
        return bytes;
    }

    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
