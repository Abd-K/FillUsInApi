package org.fillUsIn.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secretKey}")
  private String SECRET_KEY;

  @Value("${jwt.minutesValid}")
  private int MINUTES_TOKEN_VALID;

  @Value("${jwt.refreshDaysValid}")
  private int REFRESH_DAYS_TOKEN_VALID;

  public String generateJwtToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(
                    new Date(System.currentTimeMillis() + 1000 * 60 * MINUTES_TOKEN_VALID))
//            .claim("roles", user.getRoles()) // Assuming your User object has roles or authorities
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
  }

  public String validateToken(String token) {
    return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token.replace("Bearer", ""))
            .getBody()
            .getSubject();
  }
}
