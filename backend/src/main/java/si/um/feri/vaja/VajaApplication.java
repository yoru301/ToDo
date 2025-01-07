package si.um.feri.vaja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class VajaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VajaApplication.class, args);
	}

}
