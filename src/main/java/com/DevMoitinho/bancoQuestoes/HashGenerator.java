package com.DevMoitinho.bancoQuestoes;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        System.out.println(encoder.encode(""));
    }
}
