package com.hhproject.web.service;


import com.hhproject.web.exceptions.CustomExeption;
import com.hhproject.web.model.EmailBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    final JavaMailSender mailSender;
    final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(EmailBody emailBody) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springweb@mail.com");
            messageHelper.setTo(emailBody.getRecipient());
            messageHelper.setSubject(emailBody.getSubject());
            messageHelper.setText(mailContentBuilder.build(emailBody.getBody()));
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation email sended!");
        }catch (MailException e){
            throw new CustomExeption("Exeption when sending mail");
        }


    }
}
