package si.um.feri.vaja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Omogočimo CORS
                .and()
                .oauth2Login() // Omogočimo OAuth2 prijavo (Microsoft)
                .successHandler(authenticationSuccessHandler()) // Nastavimo handler za uspešno prijavo
                .and()
                .authorizeRequests()
                .requestMatchers("/api/v1/login/oauth2/code/microsoft").permitAll() // Dovoli dostop do preusmeritve
                .anyRequest().authenticated() // Zahteva prijavo za vse ostale poti
                .and()
                .logout()
                .logoutUrl("/logout") // Nastavi URL za logout
                .logoutSuccessUrl("http://localhost:3000/logout") // Preusmeri na stran za odjavo po odjavi
                .clearAuthentication(true) // Počisti vse informacije o avtentifikaciji
                .invalidateHttpSession(true) // Invalidiraj HTTP sejo
                .deleteCookies("JSESSIONID", "oauth_token"); // Izbriši piškotke (če so)

        return http.build(); // Za novejše verzije Spring Security
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("http://localhost:3000"); // Nastavi URL za preusmeritev po uspešni prijavi
        return successHandler;
    }
}
