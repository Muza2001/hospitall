package digital.one.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    public static final Long EXPIRATION_TIME = 18_000_000L;

    public String generateToken(User auth){
        return generateTokenWithUsername(auth.getUsername());
    }

    public String generateTokenWithUsername(String username) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.
                builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(EXPIRATION_TIME))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

}
