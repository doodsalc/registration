package com.test.registration.service;

import com.test.registration.data.User;

public interface EmailService {
    void sendWelcomeEmail(User user);
}
