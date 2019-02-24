package org.mql.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class DemandeAdmission {
	@Autowired
	private JavaMailSender javaMailSender ;
	public void send(String to,String from,String subject,String htmlMsg) throws MessagingException
	{
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper ;
		helper=new MimeMessageHelper(message, false, "utf-8");
		message.setContent(htmlMsg, "text/html");
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setFrom(from);
		
		javaMailSender.send(message);
	}

}
