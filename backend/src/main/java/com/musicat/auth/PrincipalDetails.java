package com.musicat.auth;


import com.musicat.data.entity.user.Authority;
import com.musicat.data.entity.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


/*
시큐리티가 /login 요청을 보고 로그인을 대신 진행해준다
로그인 진행 완료시 시큐리티 세션이 만들어진다
Authentication 객체에 유저 정보를 저장하여 시큐리티 세션에 저장
 */

/*
chatgpt4 :
Spring Security를 사용하는 경우,
시큐리티 컨텍스트에 인증 정보를 저장하는 것은 여전히 유용합니다.
이를 통해 인증된 사용자에 대한 추가적인 처리를 쉽게 구현할 수 있습니다.

JWT와 함께 Spring Security를 사용하려면 다음과 같은 설정을 적용해야 합니다:

1. JWT 토큰 생성 및 검증을 위한 클래스 구현
2. 사용자 인증 및 인가 처리를 위한 AuthenticationProvider 또는 AuthenticationManager 구현
3. JWT 필터를 사용하여 인증 요청 처리 및 토큰 전달
4. Spring Security의 설정을 수정하여 JWT 인증을 사용하도록 구성
즉, Spring Security를 사용하되, 세션을 관리하지 않고 JWT를 사용하여 인증 및 인가를 처리할 수 있습니다.
이렇게 하면 세션 관리에 따른 서버 부하를 줄이고, 확장성을 개선할 수 있습니다.

로컬 로그인을 구현하지 않을거기 때문에 implements UserDetails를 제거했다...

 */


@Data
public class PrincipalDetails implements OAuth2User {

  private User user;
  private Map<String, Object> attributes;
  private String provider;

  public PrincipalDetails(User user) {
    this.user = user;
  }

  public PrincipalDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  /**
   * 유저의 권한을 리턴하는 메소드
   *
   * @return
   */
  @Override
  @Transactional
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> listRole = new ArrayList<>();

    Set<Authority> userAuthority = user.getUserAuthority();
    for (Authority auth : userAuthority) {
      listRole.add(new SimpleGrantedAuthority(auth.getAuthorityName()));
    }

    // listRole.add(new SimpleGrantedAuthority("ROLE_USER"));
    return listRole;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  // 카카오 로그인만 하므로 이 메소드에 유저의 고유식별자를 넘겨줘야한다.
  @Override
  public String getName() {
    return user.getUserId();
  }
}
