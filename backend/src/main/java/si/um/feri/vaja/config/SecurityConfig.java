package si.um.feri.vaja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/public").permitAll()  // Public routes
                        .anyRequest().authenticated()  // All other routes require authentication
                )
                // OAuth2 login configuration
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // Custom login page (optional)
                        .defaultSuccessUrl("/home", true)  // Redirect after successful login
                )
                // Logout configuration
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // Redirect after logout
                );

        return http.build();
    }
}