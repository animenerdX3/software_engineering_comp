package bpa.dev.linavity.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    static final String FROM = "petergomesbusiness@gmail.com";
    static final String FROMNAME = "Linavity Team";
	
    static final String TO = "petergomesbusiness@gmail.com";
    
    // Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "AKIAJEWL7UE3GV5AC65Q";
    
    // Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "AscdhhWSLHmOeLtKbMu4RrbxUvkHEh+htWni3/dqrE1c";
    
    // Amazon SES SMTP host name
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    
    // The port for Amazon SES SMTP endpoint 
    static final int PORT = 587;
    
    static final String SUBJECT = "ERROR - Linavity";
    
    static String BODY;
    
    public static void makeMessage(Exception e) {
    	BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<h1>A USER HAS RECEIVED AN ERROR</h1>",
        	    ""+e.toString()+""
        	);
    }

    public static void sendEmail(Exception e) throws Exception {

    	makeMessage(e);
    	
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
