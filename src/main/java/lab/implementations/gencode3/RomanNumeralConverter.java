package lab.implementations.gencode3;

import lab.interfaces.IRomanNumeralConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RomanNumeralConverter implements IRomanNumeralConverter {

    private static final Map<Character, Integer> ROMAN_TO_ARABIC = new HashMap<>();
    private static final int[] ARABIC_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final Pattern VALID_ROMAN_PATTERN = Pattern.compile(
            "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"
    );

    static {
        ROMAN_TO_ARABIC.put('I', 1);
        ROMAN_TO_ARABIC.put('V', 5);
        ROMAN_TO_ARABIC.put('X', 10);
        ROMAN_TO_ARABIC.put('L', 50);
        ROMAN_TO_ARABIC.put('C', 100);
        ROMAN_TO_ARABIC.put('D', 500);
        ROMAN_TO_ARABIC.put('M', 1000);
    }

    @Override
    public String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        StringBuilder roman = new StringBuilder();
        int remaining = number;

        for (int i = 0; i < ARABIC_VALUES.length; i++) {
            while (remaining >= ARABIC_VALUES[i]) {
                roman.append(ROMAN_SYMBOLS[i]);
                remaining -= ARABIC_VALUES[i];
            }
        }

        return roman.toString();
    }

    @Override
    public int toArabic(String roman) {
        if (roman == null || roman.trim().isEmpty()) {
            throw new IllegalArgumentException("Римское число не может быть пустым");
        }

        String trimmedRoman = roman.trim().toUpperCase();

        if (!isValidRoman(trimmedRoman)) {
            throw new IllegalArgumentException("Некорректный формат римского числа: " + roman);
        }

        int result = 0;
        int previousValue = 0;

        for (int i = trimmedRoman.length() - 1; i >= 0; i--) {
            char currentChar = trimmedRoman.charAt(i);
            int currentValue = ROMAN_TO_ARABIC.get(currentChar);

            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            previousValue = currentValue;
        }

        return result;
    }

    private boolean isValidRoman(String roman) {
        return VALID_ROMAN_PATTERN.matcher(roman).matches();
    }
}