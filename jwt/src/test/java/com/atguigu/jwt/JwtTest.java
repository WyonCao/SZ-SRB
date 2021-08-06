package com.atguigu.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class JwtTest {

    //过期时间


    //密钥
    private static String tokenSignKey = "caoweiyao";



    private static String createToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoidGhpcyBpcyBwYWx5bG9hZCIsIm5hbWUiOiJXeW9uIn0.t5rbDJCu0_K5uG_5csIXeBZRzB5skDPxXOy0jfT1aWA";



    @Test
    //生成Token
    public void createToken(){

        String token = Jwts.builder().setHeaderParam("typ", "JWT") //令牌类型
                .setHeaderParam("alg", "HS256") //签名算法
                .claim("message", "this is palyload") //荷载，用来输入传输的信息
                .claim("name", "Wyon")
                .signWith(SignatureAlgorithm.HS256,tokenSignKey ) //哈希签名，可以加盐值
                .compact();

        System.out.println(token);


    }

    //解析Token
    @Test
    public void getTokenInfo(){

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(createToken);

        Claims claims = claimsJws.getBody();
        String subject = claims.getSubject();
        String message = claims.get("message", String.class);

        System.out.println(subject);

        System.out.println("message = " + message);

    }


}
