package br.com.academiadev.bluerefund.service;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailService {
	
	public boolean validarEmail(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
	
	public void enviaEmail(String destinatario, String novaSenha, String nome) throws MessagingException {
		 Properties props = new Properties();
		 
		 props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "465");
         
         Session session = Session.getDefaultInstance(props,
                 new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication() 
                      {
                            return new PasswordAuthentication("bluerefund@gmail.com", "beesquad");
                      }
                 });
         
        	 
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress("bluerefund@gmail.com")); //Remetente

         Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destinatario);  
         String mensagem = "Olá, "+nome+ "!\n"
         		+ "Sua nova senha é:" + novaSenha +
         		"\n\nNunca compartilhe a sua senha!";
         
         message.setRecipients(Message.RecipientType.TO, toUser);
         message.setSubject("Nova senha BlueRefund");//Assunto
         message.setText(mensagem);
         /**Método para enviar a mensagem criada*/
         Transport.send(message);


	}

}
