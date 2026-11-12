package com.hamzajg.tddandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The String Calculator kata, used here to practise test-driven development.
 *
 * <p>Rules:
 * <ul>
 *   <li>an empty string (or {@code null}) returns 0</li>
 *   <li>numbers are separated by commas or newlines</li>
 *   <li>a custom delimiter can be declared as {@code //<delimiter>\n...}</li>
 *   <li>negative numbers are rejected, and every negative is reported</li>
 *   <li>numbers greater than 1000 are ignored</li>
 * </ul>
 */
public class StringCalculator {

    private static final int MAX_VALUE = 1000;

    public int add(String numbers) {
        if (numbers == null || numbers.trim().isEmpty()) {
            return 0;
        }

        String delimiters = ",|\n";
        String body = numbers;

        if (numbers.startsWith("//")) {
            int newline = numbers.indexOf('\n');
            if (newline < 0) {
                throw new IllegalArgumentException("a custom delimiter must be followed by a newline");
            }
            delimiters = Pattern.quote(numbers.substring(2, newline));
            body = numbers.substring(newline + 1);
        }

        List<Integer> negatives = new ArrayList<>();
        int sum = 0;

        for (String token : body.split(delimiters)) {
            if (token.isEmpty()) {
                continue;
            }

            int value = Integer.parseInt(token.trim());
            if (value < 0) {
                negatives.add(value);
            } else if (value <= MAX_VALUE) {
                sum += value;
            }
        }

        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("negatives not allowed: " + join(negatives));
        }

        return sum;
    }

    private static String join(List<Integer> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(values.get(i));
        }
        return builder.toString();
    }
}
