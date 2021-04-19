package walter.study.restaurant.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {
        //Java security에서 제공하는 Key 인터페이스로 반환하며, Keys는 jwt라이브러리에서 제공하는 것을 이용한다.
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }




    public String createToken(long userId, String name) {
        //JJWT 라이브러리 사용

        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public Claims getClaims(String token) {

        //Jws : Sign이 포함된 jwt를 의미
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
