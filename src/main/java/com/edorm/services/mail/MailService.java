package com.edorm.services.mail;

import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final Configuration emailConfig;

    public MailService(JavaMailSender mailSender, @Qualifier("emailConfigBean") Configuration emailConfig) {
        this.mailSender = mailSender;
        this.emailConfig = emailConfig;
    }

    public void sendCredentialsMail(String email, String username, String password) {
        Map<String, String> model = new HashMap<>();
        model.put("username", username);
        model.put("password", password);

        sendEmail(model, email, "credentials.ftl", "Registration in EDorm");
    }

    private void sendEmail(Map<String, String> model, String email, String templateFile, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template template = emailConfig.getTemplate(templateFile);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject(subject);

            mailSender.send(message);
            log.trace("Mail has been sent");
        } catch (IOException | MessagingException | TemplateException e) {
            log.error("Error during sending mail. Message: " + e.getMessage());
        }
    }

}