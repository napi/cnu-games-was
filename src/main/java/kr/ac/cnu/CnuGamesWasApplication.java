package kr.ac.cnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class CnuGamesWasApplication {
	public static void main(String[] args) {
		SpringApplication.run(CnuGamesWasApplication.class, args);
	}
}
