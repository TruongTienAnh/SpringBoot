package com.example.demo.controller;


import com.example.demo.entity.EmailDetails;
import com.example.demo.entity.ThanhVien;
import com.example.demo.utilities.EmailService;
import com.example.demo.utilities.ThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

// Annotation
@Controller
// Class
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ThanhVienService thanhVienService;

    private int randomNum=-1;
    private int id=-1;

    public int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    // Sending a simple Email
    @PostMapping("/Quenmatkhau")
    public String sendMail(@RequestParam("MaTV") int MaTV,@RequestParam("Email") String Email) {
        randomNum = generateRandomNumber();
        ThanhVien tv = thanhVienService.getByMaTV(MaTV);
        id=MaTV;
        EmailDetails details = new EmailDetails();
        details.setRecipient(Email);
        details.setMsgBody(randomNum + "");
        details.setSubject("Code");

        String status = emailService.sendSimpleMail(details);

        return "redirect:/confirmPassword";
    }

    @GetMapping("confirmPassword")
    public String inputCode() {
        return "confirmPassword";
    }

    @PostMapping("confirmPassword")
    public String forgotEmail(@RequestParam("Code") int Code) {
        if(Code == randomNum) {
            return "redirect:/updatePassword";
        }
        else {
            return "confirmPassword";
        }
    }

    @GetMapping("updatePassword")
    public String UpdatePassword() {
        return "updatePassword";
    }

    @PostMapping("updatePassword")
    public String UpdateNewPassword(@RequestParam("newPassword") String password) {
        ThanhVien tv = thanhVienService.getByMaTV(id);
        tv.setPassword(password);
        thanhVienService.SaveThanhVien(tv);
        return "redirect:/login";
    }
}