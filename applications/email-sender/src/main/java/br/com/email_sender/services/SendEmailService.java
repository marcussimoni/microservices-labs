package br.com.email_sender.services;

import br.com.email_sender.entities.EmailMessage;
import br.com.email_sender.entities.EmailTemplate;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;

@Service
public class SendEmailService {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final String bookstoreEmail;

    public SendEmailService(
            JavaMailSender mailSender,
            FreeMarkerConfigurer freeMarkerConfigurer,
            @Value("${spring.mail.from}") String bookstoreEmail
            ) {
        this.mailSender = mailSender;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.bookstoreEmail = bookstoreEmail;
    }

    public void sendEmail(EmailMessage emailEntity, EmailTemplate template) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setFrom(bookstoreEmail);
            helper.setTo(emailEntity.getEmail());
            helper.setSubject(template.getSubject());
            helper.setText(replatePlaceholders(emailEntity, template), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to send email: " + e.getMessage(), e);
        }

    }

    private String replatePlaceholders(EmailMessage emailMessage, EmailTemplate emailTemplate) throws Exception {

        HashMap<String, String> params = new HashMap<>();

        params.put("name", emailMessage.getUserName());
        params.put("book", emailMessage.getBook());
        params.put("status", emailMessage.getStatus());
        params.put("state", emailMessage.getState());
        params.put("city", emailMessage.getCity());
        params.put("shippingId", emailMessage.getShippingId());

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(emailTemplate.getTemplate());

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

    }

}
