package com.zch1001.pi.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


@Component
public class EmailUtils {

    private static JavaMailSender mailSender;

    @Autowired
    public void setMailSender (JavaMailSender mailSender) {
        EmailUtils.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String from;


    /**


    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(content, true); // 表示可以发送HTML文件
            helper.setSubject(subject);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            log.error("发送邮件失败：{}", e.getMessage());
        }
    }

    public void sendVerificationCode(String toEmailAddress, Long code) {
        String subject = "同伴教学法支撑平台-邮箱验证码";
        Context context = new Context();
        context.setVariable("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss")));
        context.setVariable("code", code);
        String content = templateEngine.process("email_code", context);
        this.sendMail(toEmailAddress, subject, content);
    }
    */

    public void send(String to, Long code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13606102628@163.com");
        message.setTo(to);
        message.setSubject("同伴教学法支撑平台-邮箱验证码");
        message.setText("您的验证码是：" + code + "，有效期10分钟。");
        mailSender.send(message);
    }
}
