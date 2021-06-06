package org.zerock.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String pw = "1111";

        String enPw = passwordEncoder.encode(pw);

        System.out.println("enPw : " + enPw);

        boolean result = passwordEncoder.matches(pw, enPw);

        System.out.println("result : " + result);
    }
}
