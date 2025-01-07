package si.um.feri.vaja.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Omogoči CORS za vse poti
                .allowedOrigins("http://localhost:3000") // Dovoli samo zahteve z localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Dovoli te metode
                .allowCredentials(true) // Omogoči piškotke (če je potrebno)
                .allowedHeaders("*"); // Dovoli vse glave
    }
}
