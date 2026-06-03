package lab.implementations.gencode2;

import lab.interfaces.IRomanNumeralConverter;

/**
 * Конвертер римских и арабских чисел.
 *
 * Методы:
 * - toRoman(int number): конвертирует арабское число (1..3999) в римскую строку.
 * - toArabic(String roman): конвертирует корректную римскую строку в арабское число.
 *
 * Бросает IllegalArgumentException при некорректных входных данных.
 */
public class RomanNumeralConverter implements IRomanNumeralConverter {

    private static final int[] VALUES = {
            1000, 900, 500, 400,
            100,  90,  50,  40,
            10,   9,   5,   4,
            1
    };

    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV",
            "I"
    };

    // Регулярное выражение для валидации корректного римского числа в диапазоне 1..3999
    private static final String ROMAN_REGEX =
            "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    @Override
    public String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException(
                    "Аргумент number должен быть в диапазоне 1..3999 (включительно)."
            );
        }

        StringBuilder sb = new StringBuilder();
        int remaining = number;

        for (int i = 0; i < VALUES.length; i++) {
            while (remaining >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                remaining -= VALUES[i];
            }
        }

        return sb.toString();
    }

    @Override
    public int toArabic(String roman) {
        if (roman == null) {
            throw new IllegalArgumentException("Аргумент roman не должен быть null.");
        }

        String s = roman.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Аргумент roman не должен быть пустой строкой.");
        }

        if (!s.matches(ROMAN_REGEX)) {
            throw new IllegalArgumentException("Некорректный формат римского числа: " + roman);
        }

        // Парсинг: суммируем, учитывая правило вычитания
        int total = 0;
        int i = 0;
        while (i < s.length()) {
            // Попробуем взять пару символов для возможного вычитания (например CM, IV и т.д.)
            if (i + 1 < s.length()) {
                String pair = s.substring(i, i + 2);
                int pairValue = valueOf(pair);
                if (pairValue > 0) {
                    total += pairValue;
                    i += 2;
                    continue;
                }
            }
            // Иначе берем одиночный символ
            String single = s.substring(i, i + 1);
            int singleValue = valueOf(single);
            // singleValue всегда > 0, так как строка заранее валидирована
            total += singleValue;
            i++;
        }

        return total;
    }

    // Возвращает значение римской последовательности (1 или 2 символа), либо 0 если неизвестна
    private static int valueOf(String symbol) {
        switch (symbol) {
            case "M":  return 1000;
            case "CM": return 900;
            case "D":  return 500;
            case "CD": return 400;
            case "C":  return 100;
            case "XC": return 90;
            case "L":  return 50;
            case "XL": return 40;
            case "X":  return 10;
            case "IX": return 9;
            case "V":  return 5;
            case "IV": return 4;
            case "I":  return 1;
            default:   return 0;
        }
    }
}
