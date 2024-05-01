package com.theoflu.Project.CryptoLive.mail;

import jakarta.mail.MessagingException;

public interface MailService {
    String sendMail( String whoTO, String code) ;
    String sendMultiMediaMail() throws MessagingException;
}
