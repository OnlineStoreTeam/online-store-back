package com.store.utils;

import java.util.Random;

public class OrderNumberGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ORDER_NUMBER_LENGTH = 8;

    public static String generateOrderNumber() {
        StringBuilder orderNumber = new StringBuilder(ORDER_NUMBER_LENGTH);
        Random random = new Random();

        for (int i = 0; i < ORDER_NUMBER_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            orderNumber.append(randomChar);
        }

        return orderNumber.toString();
    }
}