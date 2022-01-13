package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY="NMA8JPctFuna59f5";

    public String create(UserEntity userEntity){
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                // header에 들어갈 내용 및 서명하기 위한 secret key
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload에 들어갈 내용 set
                .setSubject(userEntity.getId()) //issue subject
                .setIssuer("demo app")      //issue from what
                .setIssuedAt(new Date())    //issue at
                .setExpiration(expireDate)  //expire token date
                .compact();
    }

    public String validateAndGetUserId(String token){
        //parseClaimsJws 메서드가 Base64 디코딩 및 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후 토큰의 서명과 비교
        //위조되지 않았다면 Claim 리턴, 위조라면 예외 발생
        //그중 우리는 userId가 필요하므로 getBody를 부른다
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
