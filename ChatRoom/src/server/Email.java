/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author askm4
 */
public class Email {
    
    public void sendMail(String receiver, String content){
        final String username = "chatapp9999@gmail.com";
        final String password = "uxnfgkvtlshabbao";

        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS*/
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("chatapp9999@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver)
            );
            message.setSubject("Mã OTP kích hoạt tài khoản chat");
            message.setText("Mã OTP của bạn là: "+content+" \nLưu ý: Mã này chỉ có hiệu lực trong 10 phút!!!");

            Transport.send(message);

            System.out.println("Send email to: "+receiver);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
   
    }

