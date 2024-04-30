package dev.innomo.restapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

//JwtService is responsible for handling JWT (JSON Web Token) operations
// such as token generation, extraction of claims, and token validation.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private  String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private  int expiresInMs;

    //Generate Token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiresInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Get username from token
    // Extracts the userName from the JWT token.
    //return -> The userName contained in the token.
    public String getUsernameFromToken(String token) {
        // Extract and return the subject claim from the token
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts the userName from the JWT token.
    //return -> The userName contained in the token.
    public String extractUserName(String token) {
        // Extract and return the subject claim from the token
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts a specific claim from the JWT token.
    // claimResolver A function to extract the claim.
    // return-> The value of the specified claim.
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract the specified claim using the provided function
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //Extracts all claims from the JWT token.
    //return-> Claims object containing all claims.
    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build().parseClaimsJws(token).getBody();
    }

    //Checks if the JWT token is expired.
    //return-> True if the token is expired, false otherwise.
    public Boolean isTokenExpired(String token) {
        // Check if the token's expiration time is before the current time
        return extractExpiration(token).before(new Date());
    }

    //Validates the JWT token against the UserDetails.
    //return-> True if the token is valid, false otherwise.

    public Boolean validateToken(String token, UserDetails userDetails) {
        // Extract username from token and check if it matches UserDetails' username
        final String userName = extractUserName(token);
        // Also check if the token is expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        //Validate token exceptions
    }

    // Extracts the expiration date from the JWT token.
    //@return The expiration date of the token.
    public Date extractExpiration(String token) {
        // Extract and return the expiration claim from the token
        return extractClaim(token, Claims::getExpiration);
    }
}
