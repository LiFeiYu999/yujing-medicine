package com.yujing.medicine.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    // 密钥
    public  static String KEYSTRING="hh1GG9NI67eAIGzjx5I5TuRB31jUFNpKzRhfLpqe4ZA=";

    // 签名安全密钥
    public static SecretKey getKey() {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEYSTRING));
        return secretKey;
    }

    /**生成安全密钥，只执行一次*/
    public void genSecretKey(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
    }

    // 过期毫秒数
    public static final long EXPIRETIME=24*60*60*1000;  //1天

    /**创建JWT*/
    public static String createJWT(String username,Long id){
        //创建一个JWT构造器
        JwtBuilder builder = Jwts.builder();
        //header
        builder.setHeaderParam("alg","HS256");  //签名加密算法的类型
        builder.setHeaderParam("typ","JWT");  //token类型
        //payload
        builder.setIssuedAt(new Date());  //签发时间
        builder.setExpiration(new Date(System.currentTimeMillis()+EXPIRETIME));  //过期时间
        builder.setId(UUID.randomUUID().toString());  //JWT ID
        builder.setSubject("auth");  //主题
        builder.claim("username",username);  //自定义信息
        builder.claim("id",id);  //自定义信息
        builder.signWith(getKey());  //设置签名的密钥
        //生成签名
        String token=builder.compact();
        return token;
    }
    // 解析JWT
    public static Jws<Claims> parseJWT(String jwt){
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();  //jwt解析器
        jwtParserBuilder.setSigningKey(getKey());  //设置签名的密钥
        Jws<Claims> claimsJws = jwtParserBuilder.build().parseClaimsJws(jwt);//解析内容
        return claimsJws;

    }
    // 解析jwt字符
    public static void verifyJWT(String jwt) {
        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt);
    }

}
