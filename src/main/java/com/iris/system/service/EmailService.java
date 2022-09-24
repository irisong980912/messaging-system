package com.iris.system.service;

import com.iris.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String sendTo, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendTo);

        msg.setSubject("Please Validate Your Account");
        msg.setText("Hello there, please validate your account using the code below: /n"
                    + text);

        // TODO: send an url for validation?

        javaMailSender.send(msg);

    }
}
