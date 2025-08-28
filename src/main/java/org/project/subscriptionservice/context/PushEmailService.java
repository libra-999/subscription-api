package org.project.subscriptionservice.context;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PushEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String ownEmail;

    @SneakyThrows
    @Async
    public void sendMessage(String email, String body, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setSubject(body);
        helper.setFrom(ownEmail);
        helper.setText(subject, true);

        mailSender.send(message);
    }

}
