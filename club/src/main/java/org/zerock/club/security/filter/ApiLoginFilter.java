package org.zerock.club.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.zerock.club.security.dto.ClubAuthMemberDTO;
import org.zerock.club.security.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
// 사용자의 credential을 인증하기 위한 베이스 Filter 다.
// 웹 기반 인증요청에서 사용되는 컴포넌트로 POST 폼 데이터를 포함하는 요청을 처리
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {

        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    // Filter의 attemptAuthentication에서 this.getAuthenticationManager().authenticate(authRequest) 을 통해 알맞는 Provider를 찾아
    // authenticate로직을 실행하고 성공과 실패 결과에 따른 추가적인 작업은 Handler 등록을 통해 작업이 가능
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("-----------------ApiLoginFilter---------------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

        // UsernamePasswordAuthenticationToken 은 Authentication 인터페이스의 구현체
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("-----------------ApiLoginFilter---------------------");
        log.info("successfulAuthentication: " + authResult);

        log.info(authResult.getPrincipal());

        //email address
        String email = ((ClubAuthMemberDTO)authResult.getPrincipal()).getUsername();

        String token = null;
        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info(token);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









