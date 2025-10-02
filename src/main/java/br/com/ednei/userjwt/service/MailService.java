package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.config.VerificationEmailContent;
import br.com.ednei.userjwt.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendVerificationEmail(User user) throws MailSendException, MessagingException, UnsupportedEncodingException {
        String toAddres = user.getEmail();
        String fromAddres = "blockbitcompany@gmail.com";
        String senderName = "Blockbit";
        String subject = "Please verify your registration";

        String content = VerificationEmailContent.getContent();

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddres, senderName);
        helper.setTo(toAddres);
        helper.setSubject(subject);

        content = content.replace("[[NAME]]", user.getName());

        String verifyURL1 = "http://localhost:8080/user/verify?code=";
        String verifyURL = verifyURL1 + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        emailSender.send(message);
    }

}
