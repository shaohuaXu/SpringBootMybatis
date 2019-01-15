package com.xu.demo;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void jodaTest() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.dayOfYear().get());
    }

}
