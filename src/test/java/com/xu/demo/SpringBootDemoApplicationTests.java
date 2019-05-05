package com.xu.demo;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootDemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void jodaTest() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.dayOfYear().get());
    }

    @Test
    public void imageTest() {
        try {
            //BufferedImage bufferedImage = Thumbnails.of("D:\\a.jpg").size(1280, 1024).asBufferedImage();
            //ImageIO.write(bufferedImage, "jpg", new File("D:\\b.jpg"));

            //Thumbnails.of("D:\\a.jpg").scale(0.25f).toFile("D:\\c.jpg");

            Thumbnails.of("D:\\a.jpg").size(80, 80).keepAspectRatio(false).toFile("D:\\d.jpg");

            log.info("图片处理成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMonthDays() {
        /**
         * 表驱动法是一种编程模式（scheme）--从表里面查找信息而不使用逻辑语句（if和case）。事实上，凡是能通过逻辑语句来获得的事物，都可以通过查表来获得。
         * 对简单的情况而言，使用逻辑语句更为容易和直白。但随着逻辑链的越来越复杂，查表法也就愈发显得更具吸引力。
         */
        int month = 6;
        log.info("当前月份：{}，当月天数：{}", month, getMonthDays(month));
    }


    private int getMonthDays(int month) {
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return monthDays[--month];
    }
}
