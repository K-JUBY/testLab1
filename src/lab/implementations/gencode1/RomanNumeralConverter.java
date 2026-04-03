package lab.implementations.gencode1;

import lab.interfaces.IRomanNumeralConverter;

/**
 * Converts between Arabic and Roman numerals.
 * Supported range: 1–3999.
 */
public class RomanNumeralConverter implements IRomanNumeralConverter {

    private static final int[]    VALUES  = {1000, 900, 500, 400, 100, 90,
            50,  40,  10,  9,   5,  4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC",
            "L", "XL", "X", "IX", "V", "IV", "I"};

    @Override
    public String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException(
                    "Number must be in the range 1–3999 inclusive.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
        }
        return sb.toString();
    }

    @Override
    public int toArabic(String roman) {
        if (roman == null || roman.isEmpty()) {
            throw new IllegalArgumentException("Roman numeral must not be null or empty.");
        }

        String upper = roman.toUpperCase();
        int result = 0;
        int i = 0;

        while (i < upper.length()) {
            // Two-character symbols first
            if (i + 1 < upper.length()) {
                String two = upper.substring(i, i + 2);
                int idx = indexOf(two, SYMBOLS);
                if (idx != -1) {
                    result += VALUES[idx];
                    i += 2;
                    continue;
                }
            }
            // Single-character symbols
            String one = upper.substring(i, i + 1);
            int idx = indexOf(one, SYMBOLS);
            if (idx == -1) {
                throw new IllegalArgumentException("Invalid Roman numeral: " + roman);
            }
            result += VALUES[idx];
            i++;
        }
        // Validate canonical form by reconverting
        if (!toRoman(result).equals(upper)) {
            throw new IllegalArgumentException("Non-canonical Roman numeral: " + roman);
        }
        return result;
    }

    private static int indexOf(String symbol, String[] symbols) {
        for (int j = 0; j < symbols.length; j++) {
            if (symbols[j].equals(symbol)) {
                return j;
            }
        }
        return -1;
    }
}