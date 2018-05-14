package com.jzg.framework.utils.test.date;

import com.jzg.framework.utils.date.DateTime;
import org.junit.Test;

import java.util.Date;

public class DateTimeTest {

    @Test
    public void test(){
        DateTime dateTime = new DateTime();
        DateTime dateTime2 = new DateTime("2013-12-12");
        System.out.println("默认格式输出：" + dateTime.toDateTimeString());
        System.out.println("是否闰年:" + dateTime.isLeapYear());
        System.out.println("自定义格式输出：" + dateTime.format("yyyy-MM-dd"));
        System.out.println("输出到毫秒：" + dateTime.format("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("某月天数：" + dateTime.getDayNumsInMonth());
        System.out.println("星期：" + dateTime.getDayOfWeek()); //1:星期日,7:星期六
        System.out.println("是否周末：" + dateTime.isWeekend());
        System.out.println("相距：" + dateTime.dayNumFrom(dateTime2) + "天");

        dateTime.plusMonth(1);
        System.out.println("增加一个月后的datetime: " + dateTime.toDateTimeString());
        dateTime.plus(0, 0, 2, 4, 4, 5);
        System.out.println("增加 XXX后的datetime: " + dateTime.toDateTimeString());
        System.out.println("毫秒数：" + dateTime.getTimeInMilliSeconds());

        //DateTime转换为Date
        Date date = dateTime.getDate();
        System.out.println( dateTime.getTimeInMilliSeconds() == date.getTime());
    }
}
