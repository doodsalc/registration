package com.test.registration.service;


import com.test.registration.data.User;
import com.test.registration.helper.MailHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;


@Service
public class EmailServiceImpl implements EmailService{

    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    MailHelper mailHelper;

    @Override
    public void sendWelcomeEmail(User user) {

        String fName = Optional.ofNullable(user.getFirstName()).orElse("");
        String lName = Optional.ofNullable(user.getLastName()).orElse("");
        String fullName = fName + ((!fName.isEmpty() && !lName.isEmpty()) ? " " + lName : lName);

        logger.info("Sending welcome email to {}", fullName);

        try {
            var email = new MailHelper.MailDetails();
            email.setFrom("noreply@registration.ccc");
            email.setTo(user.getEmail());
            email.setSubject("Welcome");
            email.setMessage("Welcome " + fullName + "! You are now successfully registered.");
            mailHelper.sendEmail(email);
            logger.info("Sent welcome email to {}", fullName);
        } catch (MessagingException mex) {
            logger.error("Exception sending welcome email", mex);
        }

    }
}
