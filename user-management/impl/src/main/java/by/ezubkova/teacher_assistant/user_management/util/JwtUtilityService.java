//package by.ezubkova.teacher_assistant.user_management.util;
//
//import static java.lang.System.currentTimeMillis;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class JwtUtilityService {
//
//  @Value("${configuration.auth.jwt.signing-key}")
//  private final String jwtSigningKey;
//
//  public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
//    return Jwts.builder()
//        .setClaims(extraClaims)
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date(currentTimeMillis()))
//        .setExpiration(new Date(currentTimeMillis() + 1000 * 60 * 60 * 24))
//        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }
//
//  public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    var claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
//  private Claims extractAllClaims(String token) {
//    return Jwts.parserBuilder()
//               .setSigningKey(getSigningKey())
//               .build()
//               .parseClaimsJwt(token)
//               .getBody();
//  }
//
//  private Key getSigningKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//}
