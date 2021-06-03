package org.zerock.board;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {

    @Test
    void contextLoads() {
        String test = "abcdefg";
        String[] sArr = test.split("");
        for (String s : sArr) {
            System.out.println(s);
        }
    }

}
