package pl.alex.users.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.alex.users.ui.model.TokenResponse;

@Service
class JwtService {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration:7200000}")
  private long expiration;

  public TokenResponse generateToken(Authentication authentication) {
    return new TokenResponse(buildToken(new HashMap<>(), authentication), expiration);
  }

  public TokenResponse generateToken(Map<String, Object> claims, Authentication authentication) {
    return new TokenResponse(buildToken(claims, authentication), expiration);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractAllClaims(token).getExpiration();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getPublicSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private String buildToken(Map<String, Object> claims, Authentication authentication) {
    return Jwts
        .builder()
        .claims(claims)
        .subject((String) authentication.getPrincipal())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getPublicSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  private SecretKey getPublicSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
