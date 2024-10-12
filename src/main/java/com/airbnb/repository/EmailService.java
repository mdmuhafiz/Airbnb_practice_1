package com.airbnb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Method to send email with attachment
    public void sendEmailWithAttachment(String to, String subject, String body, String filePath) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // Use the helper class to handle attachments
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);  // true means it allows HTML content in the body
        helper.setFrom("your_email@gmail.com");

        // Attach the PDF file
        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment("Attachment.pdf", file);

        // Send the message
        javaMailSender.send(mimeMessage);
    }
}
