package si.um.feri.vaja.rest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CLIENT_ID = "ID";

    @PostMapping("/google/callback")
    public String googleCallback(@RequestBody GoogleTokenRequest googleTokenRequest) throws IOException, GeneralSecurityException {
        String idTokenString = googleTokenRequest.getToken();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            // Token je veljaven, lahko obdelamo podatke uporabnika
            GoogleIdToken.Payload payload = idToken.getPayload();
            String userId = payload.getSubject();
            // Lahko shranimo podatke v sejo ali bazo
            return "User authenticated with ID: " + userId;
        } else {
            return "Invalid ID token.";
        }
    }
}

class GoogleTokenRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
