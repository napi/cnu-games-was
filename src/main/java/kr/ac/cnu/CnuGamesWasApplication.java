package kr.ac.cnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CnuGamesWasApplication {
	public static void main(String[] args) {
		SpringApplication.run(CnuGamesWasApplication.class, args);

	}
}
