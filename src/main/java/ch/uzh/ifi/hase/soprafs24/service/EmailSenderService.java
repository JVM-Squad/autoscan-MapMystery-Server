package ch.uzh.ifi.hase.soprafs24.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Value("${app.base.url}") // Read the base URL from application.properties
    private String baseUrl;

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String toEmail,
                          String subject,
                          String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mapmysterysopra@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
