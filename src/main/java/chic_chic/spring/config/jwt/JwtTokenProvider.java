package chic_chic.spring.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private final Key key = Keys.hmacShaKeyFor("your-very-secure-and-long-secret-key-value".getBytes(StandardCharsets.UTF_8));
    private final String secretKey = "your_jwt_secret_key";
    private final long accessTokenValidity = 1000L * 60 * 60;
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;

    public String createAccessToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenValidity);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
