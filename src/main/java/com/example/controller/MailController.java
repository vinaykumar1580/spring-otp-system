package com.example.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@GetMapping("/email")
	public String sendEmail(@RequestParam(name="toEmail") String toEmail) {
		
		SimpleMailMessage mail=new SimpleMailMessage();
		
		mail.setFrom("vinaykumarbaratam1580@gmail.com");
		mail.setTo(toEmail);
		mail.setSubject("Enrolled Successfully");
		mail.setText("Congracts on enrolling on JFS Batch");
		
		javaMailSender.send(mail);
		
		return "Mail Sent SuccesFully";
	}

}
