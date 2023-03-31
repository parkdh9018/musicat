package com.musicat.jwt;


/*
유효한 자격증명을 하지 않고 접근하려 할 때 401 에러 Unauthorized 에러를 리턴
 */

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

  }
}
