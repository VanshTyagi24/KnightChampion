package ca.sheridancollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages="ca.beans, ca.sheridancollege")
public class KnightChampionApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnightChampionApplication.class, args);
	}

}
