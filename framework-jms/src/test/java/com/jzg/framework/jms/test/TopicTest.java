package com.jzg.framework.jms.test;

import com.jzg.framework.jms.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-topic.xml")
public class TopicTest {
    @Resource(name = "defaultProducer")
    private Producer producer;

    @Resource(name = "topicDestination")
    private Destination destination;

/*    @Resource(name = "topicDestination")
    private Destination ack;*/

    @Test
    public void testSend() {

        /*for (int i = 0; i < 5; i++) {
            producer.send(destination, "hello message：" + (i + 1));
        }*/


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                producer.send(destination, "hello message：" + Thread.currentThread().getId());
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            service.execute(runnable);
        }

        service.shutdown();


        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
