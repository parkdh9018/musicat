package com.musicat.jwt;

import com.musicat.auth.PrincipalDetails;
import com.musicat.data.dto.user.UserInfoJwtDto;
import com.musicat.data.entity.user.Authority;
import com.musicat.data.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "userRole"; // 권한
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;


    // 처음 값 주입
    public TokenProvider(
            @Value("${jwt.secret}") String  secret,
            @Value("${jwt.token-validate-time}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }


    // Bean 생성 후 주입 받은 후 secret 값을 Base64 Decode 해서 Key 변수에 할당
    @Override
    public void afterPropertiesSet()  {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체의 권한 정보를 이용해서 Token을 생성하는 메소드
    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 토큰에 담을 정보
        Long userSeq = principalDetails.getUser().getUserSeq();
        String userNickname = principalDetails.getUser().getUserNickname();
        String userProfileImage = principalDetails.getUser().getUserProfileImage();
        boolean userIsChattingBan = principalDetails.getUser().isUserIsChattingBan();
        boolean userIsBan = principalDetails.getUser().isUserIsBan();

        // 토큰 유효 시간
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userSeq.toString())
                .claim(AUTHORITIES_KEY, authorities) // 권한 정보
                .claim("userNickname", userNickname)
                .claim("userProfileImage", userProfileImage)
                .claim("userIsChattingBan", userIsChattingBan)
                .claim("userIsBan", userIsBan)
                .signWith(key, SignatureAlgorithm.HS256) // jwt에 서명할 암호화 키와 알고리즘을 설정
                .setExpiration(validity) // jwt 만료 시간 설정
                .compact();
    }

    public String createUserToken(User user) {

        String authorities = user.getUserAuthority().stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserSeq()))
                .claim(AUTHORITIES_KEY, authorities) // 권한 정보
                .claim("userNickname", user.getUserNickname())
                .claim("userProfileImage", user.getUserProfileImage())
                .claim("userIsChattingBan", user.isUserIsChattingBan())
                .claim("userIsBan", user.isUserIsBan())
                .signWith(key, SignatureAlgorithm.HS256) // jwt에 서명할 암호화 키와 알고리즘을 설정
                .setExpiration(validity) // jwt 만료 시간 설정
                .compact();

    }

    /*
    Token에 담겨 있는 정보를 이용해서 Authentication 객체를 리턴하는 메소드
    1. JWT를 파싱해 클레임을 획득
    2. 클레임에서 사용자 정보 및 권한 정보 추출
    3. 사용자 정보와 권한 정보를 이용해 Authentication 객체를 생성
     */
    public Authentication getAuthentication(String token) {

        // JWT 파싱
        Jws<Claims> parsedJwt = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims claims = parsedJwt.getBody();

        // 클레임에서 사용자 정보와 권한 정보 추출
        String userSeq = claims.getSubject();
        List<String> authorities = (List<String>) claims.get(AUTHORITIES_KEY);

        // 권한 정보를 GrantedAuthority 객체의 리스트로 변환
        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userSeq, null, grantedAuthorities);

        return authentication;
    }


    /*
    token 유효성 검증 메소드
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }


    /*
    token에서 사용자 정보를 추출하는 메소드
     */
    public UserInfoJwtDto getUserInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


        // System.out.println(claims.get(AUTHORITIES_KEY, String.class));



        return UserInfoJwtDto.builder()
                .userSeq(Long.valueOf(claims.getSubject()))
                .userAuthority(claims.get(AUTHORITIES_KEY, String.class))
                .userNickname(claims.get("userNickname", String.class))
                .userProfileImage(claims.get("userProfileImage", String.class))
                .userIsChattingBan(claims.get("userIsChattingBan", Boolean.class))
                .userIsBan(claims.get("userIsBan", Boolean.class))
                .build();
    }


    public Map<String, Object> getUserIdFromJWT(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


        logger.info("userSeq:"+claims.getSubject());
        resultMap.put("userSeq", claims.getSubject());
        return resultMap;
    }







}
