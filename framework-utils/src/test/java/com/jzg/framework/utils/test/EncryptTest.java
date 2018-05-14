package com.jzg.framework.utils.test;

import com.jzg.framework.utils.encrypt.Md5Encrypt;
import com.jzg.framework.utils.encrypt.ThreeDES;
import org.junit.Test;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/7/29 19:37
 */
public class EncryptTest {

    @Test
    public void testMd5Encypt(){
         //E10ADC3949BA59ABBE56E057F20F883E

        String strRes = "123456";

        String strDes = Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt(strRes, "utf-8"));
        System.out.println(strDes);

        System.out.println("test 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("test","utf-8")));
        System.out.println("admin 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("admin","utf-8")));
        System.out.println("test1 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("test1","utf-8")));
        System.out.println("zhang 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("zhang","utf-8")));
        System.out.println("dealer 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("dealer","utf-8")));
        System.out.println("123456 加密：" + Md5Encrypt.toHexStringUpper(Md5Encrypt.encrypt("123456","utf-8")));
    }


    @Test
    public void testDesEncypt(){
        //E10ADC3949BA59ABBE56E057F20F883E

        String strRes = "123456";

        String strDes = ThreeDES.encrypt(strRes);
        System.out.println(strDes);
        System.out.println("test 加密：" + ThreeDES.encrypt("test"));
        System.out.println("admin 加密：" + ThreeDES.encrypt("admin"));
        System.out.println("test1 加密：" + ThreeDES.encrypt("test1"));
        System.out.println("zhang 加密：" + ThreeDES.encrypt("zhang"));
        System.out.println("dealer 加密：" + ThreeDES.encrypt("dealer"));
        System.out.println("123456 加密：" + ThreeDES.encrypt("123456"));
    }

    @Test
    public void testDesDecypt(){
        //E10ADC3949BA59ABBE56E057F20F883E

        String strRes = "MtN6vZrV2nw=";//test

        String strDes = ThreeDES.decrypt(strRes);
        System.out.println(strDes);
    }

}
