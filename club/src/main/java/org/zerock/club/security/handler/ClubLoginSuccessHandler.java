package org.zerock.club.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    // 스프링 시큐리티가 화면 이동에 대한 규칙을 정의한 인터페이스로 이 인터페이스의 구현 객체로 화면 redirect 를 할 수 있다.
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("--------------------------------------");
        log.info("onAuthenticationSuccess");

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();
        log.info("authMember : " + authMember);

        boolean fromSocial = authMember.isFromSocial();

        log.info("Need Modify Member? " + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        log.info("passwordResult : " + passwordResult);
        log.info("authMember.getPassword() : " + authMember.getPassword());

        if(fromSocial && passwordResult) {
            log.info("modify member");
            redirectStratgy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }

}
