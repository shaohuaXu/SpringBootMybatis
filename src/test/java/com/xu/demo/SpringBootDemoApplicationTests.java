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
}
