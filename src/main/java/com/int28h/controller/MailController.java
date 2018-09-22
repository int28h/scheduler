package com.int28h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class MailController {
	@Autowired
	private JavaMailSender sender;
	
	@RequestMapping("/sendMail")
    	public String sendMail(@RequestParam(value="email", defaultValue="null") String sendTo) {
        	MimeMessage message = sender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(message);

        	try {
			helper.setFrom("TEST@gmail.com");
            		helper.setTo(sendTo);
           		helper.setText("Пх’нглуи мглв’нафх Ктулху Р’льех вгах’нагл фхтагн!");
            		helper.setSubject("Mail From Spring Boot");
        	} catch (MessagingException e) {
			return("Mail to " + sendTo + " wasn't sent.");
        	}
        	sender.send(message);
        	return("Mail to " + sendTo + " was sent.");
    	}
}
