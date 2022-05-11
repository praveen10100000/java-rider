package com.codewithpp.JavaDriver.service;

import com.codewithpp.JavaDriver.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ServiceImplementation implements com.codewithpp.JavaDriver.service.Service {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private DriverRepo driverRepo;

    @Override
    public int generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(90000);
        return otp;

    }

    public void sendMail(String subject, String From, String To, String kya) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.addTo(To);
            mimeMessageHelper.setFrom(From);
            mimeMessageHelper.setText(kya, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
