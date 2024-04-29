package com.example.birthdate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BirthDateApplicationTests {

    @Test
    void contextLoads() {
    }

}
