package com.musicat.handler;

import com.musicat.jwt.TokenProvider;
import com.musicat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*

OAuth2 인증 성공 핸들러

요약하면, 이 코드는 사용자가 OAuth2 인증에 성공한 후 실행되며,
인증된 사용자를 대상 URL로 리다이렉트하고 생성된 JWT 토큰을 URL의 쿼리 파라미터로 추가합니다.
이를 통해 클라이언트는 JWT 토큰을 수신하여 인증 상태를 유지할 수 있습니다.


 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserService userService;

    // 인증이 성공한 경우 호출
    // 인증된 사용자를 대상 URL로 리다이렉트
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // redirect 할 url을 지정해준다
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        


        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 인증된 사용자를 리다이렉트할 대상 URL을 결정
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // String targetUrl = "/except/login-success?";
        String targetUrl = "/except/login-success?";

        // 인증 정보를 기반으로 JWT 토큰 생성
        String token = tokenProvider.createToken(authentication);

        // URL 쿼리 파라미터로 token 추가
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

}


/*
이 코드는 Spring Security와 함께 사용되는 OAuth2 인증 성공 핸들러입니다. 사용자가 OAuth2 인증을 성공적으로 완료하면 실행되는 코드입니다. 이 코드의 주요 목적은 인증된 사용자를 대상 URL로 리다이렉트하는 것입니다.

OAuth2AuthenticationSuccessHandler 클래스는 SimpleUrlAuthenticationSuccessHandler를 확장하므로, 인증 성공 시 로직을 정의할 수 있습니다.

TokenProvider는 JWT 토큰을 생성하고 검증하는 클래스입니다. 이 클래스는 이 핸들러에서 사용됩니다.

onAuthenticationSuccess 메소드는 인증이 성공한 경우 호출됩니다. 이 메소드는 인증된 사용자를 대상 URL로 리다이렉트합니다.

determineTargetUrl 메소드는 인증된 사용자를 리다이렉트할 대상 URL을 결정합니다. 여기서는 대상 URL을 "/"로 설정하고 있습니다.

JWT 토큰 생성: tokenProvider.createToken(authentication)을 사용하여 인증 객체를 기반으로 JWT 토큰을 생성합니다.

생성된 JWT 토큰은 대상 URL의 쿼리 파라미터로 추가됩니다. 이를 위해 UriComponentsBuilder.fromUriString(targetUrl).queryParam("jwt", jwt).build().toUriString()를 사용하여 URL을 구성합니다.

요약하면, 이 코드는 사용자가 OAuth2 인증에 성공한 후 실행되며, 인증된 사용자를 대상 URL로 리다이렉트하고 생성된 JWT 토큰을 URL의 쿼리 파라미터로 추가합니다. 이를 통해 클라이언트는 JWT 토큰을 수신하여 인증 상태를 유지할 수 있습니다.
 */