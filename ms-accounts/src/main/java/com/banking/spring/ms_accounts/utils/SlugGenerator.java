package com.banking.spring.ms_accounts.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public final class SlugGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private SlugGenerator() {
    }

    public static String generate(int length) {
        return RANDOM.ints(length, 0, ALPHABET.length()).mapToObj(i -> String.valueOf(ALPHABET.charAt(i)))
                .collect(Collectors.joining());
    }

    public static String generate() {
        return generate(8);
    }

}
