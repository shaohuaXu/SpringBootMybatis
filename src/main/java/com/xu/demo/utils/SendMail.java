package com.xu.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * JavaMailSender 发送邮件
 * 1.pom.xml文件引入Jar包
 * 2.application.yaml中配置相应属性
 *
 * @author shaohua
 * @date 2020-01-14.
 */
@Slf4j
@Component
public class SendMail {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;

    @Autowired
    TemplateEngine templateEngine;

    /**
     * 可以在该方法中加一个对象或者集合来解决动态发送问题
     */
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        //直接读取配置文件中配置的username属性值
        message.setFrom(mailProperties.getUsername());
        //接收人邮箱地址
        message.setTo("470243468@qq.com");
        //邮件主题
        message.setSubject("it is a test for spring boot");
        //邮件内容
        message.setText("你好，我是少华，我在测试发送邮件。");
        try {
            javaMailSender.send(message);
            log.info("邮件发送完毕。");
        } catch (Exception e) {
            log.error("邮件发送异常 e", e);
        }
    }

    /**
     * 按照既有邮件模板，发送邮件
     * 邮件模板在 /resources/templates 目录下
     */
    public void sendMail2() {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo("470243468@qq.com");
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("入职欢迎");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name", "徐海星");
            context.setVariable("posName", "徐先生");
            context.setVariable("joblevelName", "攻城狮");
            context.setVariable("departmentName", "攻城部");
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            javaMailSender.send(msg);
            log.info("邮件发送完毕。");
        } catch (MessagingException e) {
            log.error("邮件发送失败：", e);
        }
    }

    /**
     * 发送带附件的邮件
     */
    public void sendMailWilthAttachment() {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
            helper.setFrom("18610907663@163.com");
            helper.setTo("470243468@qq.com");
            helper.setSubject("it is a test for spring boot");
            helper.setText("你好，我是少华，我在测试发送邮件。");
            FileSystemResource file = new FileSystemResource(new File("D:\\1.jpg"));
            helper.addAttachment("附件-1.jpg", file);
            javaMailSender.send(mimeMailMessage);
            log.info("带附件的邮件发送完毕。");
        } catch (Exception e) {
            log.error("邮件发送异常 ", e);
        }
    }

    /**
     * 发送带有静态资源的邮件
     */
    public void sendMailWithStaticResource() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("18610907663@163.com");
            helper.setTo("470243468@qq.com");
            helper.setSubject("主题：嵌入静态资源");
            helper.setText("<html><body><img src=\"cid:hello\"></body></html>", true);
            FileSystemResource file = new FileSystemResource(new File("D:\\1.jpg"));
            //注意addInline()中资源名称 hello 必须与 text正文中cid:hello对应起来
            helper.addInline("hello", file);
            javaMailSender.send(message);
            log.info("带有静态资源的邮件发送完毕。");
        } catch (Exception e) {
            log.error("邮件发送异常 ", e);
        }
    }
}
