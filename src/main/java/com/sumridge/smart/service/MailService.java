package com.sumridge.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Created by liu on 16/11/14.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String toAddress, String title, String content) {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage);
            mailMessage.setTo(toAddress);
            mailMessage.setSubject(title);
            mailMessage.setText(content, true);
            mailMessage.setFrom("xman2054@163.com");
        };

        javaMailSender.send(preparator);
    }

    public String buildMailContent(String template, Map<String, Object> params) {

        Context context = new Context();
        params.forEach((k,v) -> {
            context.setVariable(k, v);
        });
        return templateEngine.process(template, context);
    }
}
