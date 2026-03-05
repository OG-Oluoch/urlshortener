package com.spring.urlshortener.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom secureRandom;

    public ShortCodeGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    //generate a random short code of length 6 using base62 characters

    public String generate(){

        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for(int i=0; i<CODE_LENGTH; i++){

            int randIndex = secureRandom.nextInt(BASE62_CHARS.length());

            code.append(BASE62_CHARS.charAt(randIndex));

        }

        return code.toString();
    }
}
