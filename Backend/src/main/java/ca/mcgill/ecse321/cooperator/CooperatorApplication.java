package ca.mcgill.ecse321.cooperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EntityScan("ca.mcgill.ecse321.model")
@SpringBootApplication
public class CooperatorApplication{
   public static void main(String[] args) {
      SpringApplication.run(CooperatorApplication.class, args);
   }
   
}