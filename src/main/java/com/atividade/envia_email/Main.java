package com.atividade.envia_email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Main {
	static final String from = "exemplo@exemplo.com";
	static final String to = "exemplo@exemplo.com";
	static final String username = "exemplo@exemplo.com";
	static final String password = "senha";

	static final String host = "smtp.gmail.com";
	// The port you will connect to on the SMTP endpoint. Port 25 or 587 is allowed.
	static final int port = 587;

	public static void main(String[] args) {
		Properties props = System.getProperties();
		// SMTP(Simple Mail Transfer Protocol) é um protocolo de comunicação usado para
		// enviar e-mails pela internet.
		props.setProperty("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");

		// Autenticando senha do gmail.
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject("Currículo enviado com Java");
			BodyPart msgBody = new MimeBodyPart();
			msgBody.setText("Esse email com currículo em anexo foi enviado com Java.");

			Multipart bodyAndFile = new MimeMultipart();
			bodyAndFile.addBodyPart(msgBody);

			// Criando parte do anexo e juntando com o corpo do email.
			msgBody = new MimeBodyPart();
			String filename = "/home/almaviva-linux/Downloads/Profile.pdf";
			DataSource source = new FileDataSource(filename);
			msgBody.setDataHandler(new DataHandler(source));
			msgBody.setFileName(filename);
			bodyAndFile.addBodyPart(msgBody);

			msg.setContent(bodyAndFile);
			Transport.send(msg);
			System.out.println("O email foi enviado!");
			
		} catch (Exception mex) {
			System.out.println("O email não foi enviado");
			System.out.println("Erro: " + mex.getMessage());
		}
	}
}
