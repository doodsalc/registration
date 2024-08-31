package com.test.registration.service;

import com.test.registration.data.User;
import com.test.registration.helper.MailHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = EmailServiceImpl.class)
@TestPropertySource(properties = {
        "smtp.server.host=smtphost",
        "task.default.priority=high"
})
class EmailServiceImplTest {

    @MockBean
    MailHelper mailHelper;

    @Autowired
    EmailServiceImpl emailService;

    @BeforeAll
    static void setUp() {
        System.setProperty("smtp.server.host", "stmp.host");
        System.setProperty("smtp.server.port", "111");
    }

    @Test
    void shouldSendEmail() throws MessagingException {
        emailService.sendWelcomeEmail(new User());
        verify(mailHelper, times(1)).sendEmail(any(MailHelper.MailDetails.class));
    }
}