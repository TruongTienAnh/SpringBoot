package com.example.demo.utilities;

import com.example.demo.entity.EmailDetails;

public interface EmailService {


    String sendSimpleMail(EmailDetails details);
}