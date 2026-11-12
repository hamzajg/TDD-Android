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
 *   <li>a delimiter of any length, or several of them, can be declared in
 *       brackets: {@code //[***]\n...} or {@code //[*][%]\n...}</li>
 *   <li>negative numbers are rejected, and every negative is reported</li>
 *   <li>numbers greater than 1000 are ignored</li>
 * </ul>
 */
public class StringCalculator {

    private static final int MAX_VALUE = 1000;
    private static final String DEFAULT_DELIMITERS = ",|\n";

    public int add(String numbers) {
        if (numbers == null || numbers.trim().isEmpty()) {
            return 0;
        }

        String delimiters = DEFAULT_DELIMITERS;
        String body = numbers;

        if (numbers.startsWith("//")) {
            int newline = numbers.indexOf('\n');
            if (newline < 0) {
                throw new IllegalArgumentException("a custom delimiter must be followed by a newline");
            }

            String header = numbers.substring(2, newline);
            body = numbers.substring(newline + 1);

            if (header.startsWith("[")) {
                delimiters = quotedBracketedDelimiters(header);
            } else {
                if (header.isEmpty()) {
                    throw new IllegalArgumentException("a custom delimiter cannot be empty");
                }
                delimiters = Pattern.quote(header);
            }
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

    /**
     * Turns {@code [***]} or {@code [*][%]} into an alternation of quoted
     * delimiters. Longest first, so {@code **} is never read as two {@code *}.
     */
    private static String quotedBracketedDelimiters(String header) {
        List<String> quoted = new ArrayList<>();
        int index = 0;

        while (index < header.length()) {
            if (header.charAt(index) != '[') {
                throw new IllegalArgumentException("a bracketed delimiter must be written as [name]: " + header);
            }
            int end = header.indexOf(']', index);
            if (end < 0) {
                throw new IllegalArgumentException("a bracketed delimiter is missing its closing ]: " + header);
            }

            String delimiter = header.substring(index + 1, end);
            if (delimiter.isEmpty()) {
                throw new IllegalArgumentException("a custom delimiter cannot be empty");
            }
            quoted.add(Pattern.quote(delimiter));
            index = end + 1;
        }

        // Longest first: java.util.regex takes the first alternative that matches.
        quoted.sort((first, second) -> second.length() - first.length());

        StringBuilder alternation = new StringBuilder();
        for (String delimiter : quoted) {
            if (alternation.length() > 0) {
                alternation.append('|');
            }
            alternation.append(delimiter);
        }
        return alternation.toString();
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
