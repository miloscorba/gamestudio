package sk.tuke.gamestudio.service;

import org.jboss.weld.context.ejb.Ejb;
import sk.tuke.gamestudio.client.MailUtil;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(mappedName = "jms/achievementQueue")
public class ContactWinnerBean implements MessageListener {
    @EJB
    private ScoreService scoreService;

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "BestBookShooop@gmail.com";
    private String PASSWORD = "BestBookShooop15";
    private String FROM_NAME = "BookSHooop";

    public boolean sendMail(String[] recipients, String subject, String message) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getInstance(props, new ContactWinnerBean.SocialAuth());
            javax.mail.Message msg = new MimeMessage(session);

            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
            msg.setFrom(from);

            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                toAddresses[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(javax.mail.Message.RecipientType.TO, toAddresses);

            msg.setSubject(subject);
            msg.setContent(message, "text/plain");
            Transport.send(msg);
            return true;
        } catch (UnsupportedEncodingException | MessagingException ex) {
            Logger.getLogger(ContactWinnerBean.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    class SocialAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

        }
    }

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                throw  new RuntimeException(e);
            }
        }
        System.out.println(">>>>>>" + message);
    }
}