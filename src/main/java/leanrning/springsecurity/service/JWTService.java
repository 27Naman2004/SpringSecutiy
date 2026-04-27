package leanrning.springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import leanrning.springsecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;


@Service
public class JWTService {

    private String secretKey = "qtKS6ga8Ziiod/UCrgrlIRuJmwbGISYW7f+vz2jQ/xs=";


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return builder().claims()
                .add(claims).subject(user.getUsername()).issuer("Naman").issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 60*10*1000))
                .and().signWith(getKey()).compact();
    }


    private SecretKey getKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {

        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);

        return username.equals(userDetails.getUsername()) && (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
