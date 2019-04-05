package ca.mcgill.ecse321.cooperator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;

import ca.mcgill.ecse321.cooperator.gmail.GmailService;

@SpringBootApplication
public class CooperatorApplication {
	public static void main(String[] args) throws IOException, GeneralSecurityException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, GmailService.JSON_FACTORY, GmailService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(GmailService.APPLICATION_NAME)
                .build();
        new GmailService(service);
        //GmailService.sendEmail("matusu36@gmail.com", "hi");
		SpringApplication.run(CooperatorApplication.class, args);
	}
}