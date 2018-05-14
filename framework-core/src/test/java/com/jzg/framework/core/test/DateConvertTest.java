package com.jzg.framework.core.test;

import com.jzg.framework.core.web.converter.DateConverter;
import org.junit.Test;

import java.util.Date;

public class DateConvertTest {
    @Test
    public void test(){
        DateConverter converter = new DateConverter();
        Date date = converter.convert("Tue Mar 01 00:00:00 CST 2016");
        System.out.println(date);
    }

    public void testRegex(){
        String source= "Tue Mar 01 00:00:00 CST 2016";
        boolean bRet = source.matches("^[A-Z][a-z]{2}\\s+[A-Z][a-z]{2}\\s+\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}\\s+CST\\s+\\d{4}$");
        System.out.println(bRet);
    }
}
