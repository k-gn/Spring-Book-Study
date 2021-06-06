package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("exAll.............");
    }

    @GetMapping("/member")
    // @AuthenticationPrincipal : UserDetailsService에서 Return한 객체 파라메터로 직접 받아 사용할 수 있다.
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMember.............");
        log.info(clubAuthMember);
    }

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin.............");
    }
}
