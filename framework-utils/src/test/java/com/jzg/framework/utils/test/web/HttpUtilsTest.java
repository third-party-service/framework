package com.jzg.framework.utils.test.web;

import com.jzg.framework.utils.date.DateTime;
import com.jzg.framework.utils.test.model.OrderQueryVo;
import com.jzg.framework.utils.web.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
public class HttpUtilsTest {

    @Test
    public void test() {
        String url = "";
        String result = "";

        /*url = "http://ptvapi.guchewang.com/appv5/GetMakeModelStyleAll.ashx?op=GetModel&MakeId=9&sign=66B12A7403663445629DB9DC9BA9D3D0";
        result = HttpUtils.doGet(url);
        System.out.println("********************");
        System.out.println("url:  " + url);
        System.out.println("********************");
        System.out.println(result);*/


        url = "http://ptvapi.guchewang.com/appv5/GetMakeModelStyleAll.ashx";
        Map<String, Object> map = new HashMap<>();
        map.put("op", "GetModel");
        map.put("MakeId", "9");
        map.put("sign", "66B12A7403663445629DB9DC9BA9D3D0");

        result = HttpUtils.doGet(url, map);
        System.out.println("********************");
        System.out.println("url:  " + url);
        System.out.println("********************");
        System.out.println(result);


        //url = "http://cheshangapisandbox.jingzhengu.com/area/getAreaListById?areaId=1&sign=11111";
        url = "http://cheshangapisandbox.jingzhengu.com/area/getAreaListById";
        Map<String, Object> mapPost = new HashMap<>();
        mapPost.put("areaId", "1");
        mapPost.put("sign", "11111");
        result = HttpUtils.doPost(url, mapPost);
        System.out.println("********************");
        System.out.println("url:  " + url);
        System.out.println("********************");
        System.out.println(result);
    }

    @Test
    public void testDate() {
        try {
            String serviceUrl = "http://localhost:8080/order/getOrdersAndContact";
            OrderQueryVo orderQueryVo = new OrderQueryVo();
            DateTime dateTime = new DateTime("2016-03-01 12:01:30");
            orderQueryVo.setStartTime(dateTime.getDate());
            orderQueryVo.setOrderStatus(-4);
            Map params = com.jzg.framework.utils.bean.BeanUtilsI.getFields(orderQueryVo);

            String result = HttpUtils.doGet(serviceUrl, params);
            System.out.println("********************");
            System.out.println(result);


            result = HttpUtils.doGet(serviceUrl, params);
            System.out.println("********************");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testWcf() {
        try {
            String serviceUrl = "http://localhost:8080/order/getOrdersAndContact";
            OrderQueryVo orderQueryVo = new OrderQueryVo();
            DateTime dateTime = new DateTime("2016-03-01 12:01:30");
            orderQueryVo.setStartTime(dateTime.getDate());
            orderQueryVo.setOrderStatus(-4);
            Map params = com.jzg.framework.utils.bean.BeanUtilsI.getFields(orderQueryVo);

            String result = HttpUtils.doGet(serviceUrl, params);
            System.out.println("********************");
            System.out.println(result);


            result = HttpUtils.doGet(serviceUrl, params);
            System.out.println("********************");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1() {
        String encoding = "UTF-8";
        String str = "测试";
        try {

            System.out.println(String.format("%s decode: %s", str, URLDecoder.decode(str, encoding)));
            str = URLEncoder.encode(str, encoding);
            System.out.println(str);


            str = URLEncoder.encode(str, encoding);
            System.out.println(str);


            str = URLEncoder.encode(str, encoding);
            System.out.println(str);

            System.out.println(String.format("%s decode: %s", str, URLDecoder.decode(str, encoding)));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
