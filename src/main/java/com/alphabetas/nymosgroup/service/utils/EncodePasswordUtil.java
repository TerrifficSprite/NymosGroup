package com.alphabetas.nymosgroup.service.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePasswordUtil {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password){
        return passwordEncoder.encode(password);
    }

    public static boolean equal(String password, String password_2){
        return passwordEncoder.matches(password, password_2);
    }
}
