package com.sem.project.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender sender;
	
	public boolean sendEmail(String to, String subject, String body) {
		
		try {
			MimeMessage createMimeMessage = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			sender.send(createMimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
