package com.example.androdid.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailUtil {

    public static String sendEmail(String toEmail) {
        // 生成随机六位验证码
        String verificationCode = generateVerificationCode();

        // 设置发件人和收件人的电子邮件地址
        String fromEmail = "2391835196@qq.com";
        String password = "zberqornlsclechg";

        // 设置邮件服务器的属性,授权码zberqornlsclechg
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // 设置接收邮件服务器的属性
        properties.put("mail.imap.host", "imap.qq.com");
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        // 创建 Session 对象
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 创建 MimeMessage 对象
            Message message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(fromEmail));

            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // 设置主题
            message.setSubject("验证码");

            // 设置邮件内容
            message.setText("您的验证码是：" + verificationCode);

            // 发送邮件
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return verificationCode;
    }

    private static String generateVerificationCode() {
        // 生成随机六位验证码
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}