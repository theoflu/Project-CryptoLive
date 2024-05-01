package com.theoflu.Project.CryptoLive.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{


    private final JavaMailSender mailSender;
    @Override
    public String sendMail( String whoTO, String code) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("noreply@theoflu.com");
        message.setTo(whoTO);
        message.setText("Selamlar, İşte yeni Stream Key : " +code);
        message.setSubject("Stream Key ");
        mailSender.send(message);
        return "Gönderildi";
    }

    @Override // multimedya göndermey istersek
    public String sendMultiMediaMail() throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
        message.setFrom("noreply@theoflu.com");
        message.setTo("yusuf-oflu61@hotmail.com");
        message.setText("Selamlar Bu Mesaj Size Gönderildi");
        message.setSubject("Sakın Açmaaa!!!!");
        FileSystemResource file=new FileSystemResource(new File("D:\\Background\\mongodb.png"));
        message.addAttachment("mongo.png",file);
        mailSender.send(mimeMessage);
        return "Gönderildi";
    }
}
