package lab8.lab8;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lab8.lab8.Util.Jwt;

@SpringBootApplication
public class Lab8Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Lab8Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Jwt jwt = new Jwt();
		System.out.println(jwt.generateToken("Saikhnaa", "ADMIN"));
	}

}
