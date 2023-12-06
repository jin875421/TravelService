package com.example.androdid.utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class UniqueRandomCodeGenerator {

    private static Set<String> generatedCodes = new HashSet<>();

    public static String generateUniqueRandomCode() {
        int maxAttempts = 1000; // 设置最大尝试次数，防止无限循环

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            String code = generateRandomCode();

            if (generatedCodes.add(code)) {
                return code; // 如果生成的代码是唯一的，则返回
            }
        }

        throw new RuntimeException("Unable to generate unique random code");
    }

    private static String generateRandomCode() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }

}
