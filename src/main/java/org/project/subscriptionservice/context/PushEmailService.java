package org.project.subscriptionservice.context;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PushEmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String ownEmail;

    @SneakyThrows
    public void sendCancelMessage (String email, String reason){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setSubject("Your subscription has been cancelled , if you want to renew your subscription please contact us! ");
        helper.setFrom(ownEmail);
        helper.setText("<p> " + reason +" </p>\n", true);

        mailSender.send(message);
    }

    public void sendSubscribeMessage (String email, String message){
    }

    public void sendInvoiceMessage (){
    }

}
