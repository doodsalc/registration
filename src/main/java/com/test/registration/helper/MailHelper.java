package com.test.registration.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailHelper {

    @Value("${smtp.server.host}")
    private String smtpHost;
    @Value("${smtp.server.port}")
    private String smtpPort;

    public static class MailDetails {

        private String to;
        private String from;
        private String subject;
        private String message;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public void sendEmail(MailDetails email) throws MessagingException {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email.getFrom()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());
        Transport.send(message);
    }
}
