package com.account.securities;

import com.account.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String secret = "account";

    public String generateToken(User user){

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("email",user.getEmail());
        claims.put("company",user.getCompany());
        claims.put("role",user.getRole());

        return createToken(claims);
    }

    private String createToken(Map<String,Object>map){
        String token = Jwts.builder()
                .setClaims(map)
                .setSubject(map.get("email").toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

        return token;
    }



    private Claims extractAllClaims(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }


    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }


    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String currentUser = extractAllClaims(token).get("id").toString();
        return (currentUser.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

}
