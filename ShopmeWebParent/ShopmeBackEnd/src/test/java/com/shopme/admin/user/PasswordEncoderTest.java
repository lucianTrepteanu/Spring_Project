package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);

        System.out.println(encodedPassword);

        boolean matches = passwordEncoder.matches(password, encodedPassword);

        assertThat(matches).isTrue();
    }
}
