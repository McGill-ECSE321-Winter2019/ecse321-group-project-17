package ca.mcgill.ecse321.cooperator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CooperatorApplication {
	public static void main(String[] args) throws IOException, GeneralSecurityException{
		SpringApplication.run(CooperatorApplication.class, args);
	}
}