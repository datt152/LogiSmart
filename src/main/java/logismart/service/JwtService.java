package logismart.service;


import io.jsonwebtoken.Claims;
import java.util.function.Function;

public interface JwtService {
    String generateToken(String username);
    String extractUsername(String token);
    boolean isTokenValid(String token, String username);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
