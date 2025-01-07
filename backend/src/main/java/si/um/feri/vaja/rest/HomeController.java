package si.um.feri.vaja.rest;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(Principal principal) {
        OAuth2User oAuth2User = (OAuth2User) principal;
        String username = oAuth2User.getAttribute("name"); // Get user’s name
        return "Welcome, " + username;
    }
}