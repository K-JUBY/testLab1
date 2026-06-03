package lab.services;

import lab.interfaces.IRomanNumeralConverter;

/**
 * Сервис для выполнения арифметических операций над римскими числами.
 * Использует IRomanNumeralConverter для преобразования между форматами.
 */
public class RomanNumeralCalculator {
    private final IRomanNumeralConverter converter;

    public RomanNumeralCalculator(IRomanNumeralConverter converter) {
        this.converter = converter;
    }

    /**
     * Складывает два римских числа.
     */
    public String add(String roman1, String roman2) {
        int val1 = converter.toArabic(roman1);
        int val2 = converter.toArabic(roman2);
        return converter.toRoman(val1 + val2);
    }

    /**
     * Вычитает второе римское число из первого.
     */
    public String subtract(String roman1, String roman2) {
        int val1 = converter.toArabic(roman1);
        int val2 = converter.toArabic(roman2);
        if (val1 <= val2) {
            throw new IllegalArgumentException("Результат вычитания должен быть больше 0.");
        }
        return converter.toRoman(val1 - val2);
    }

    /**
     * Умножает два римских числа.
     */
    public String multiply(String roman1, String roman2) {
        int val1 = converter.toArabic(roman1);
        int val2 = converter.toArabic(roman2);
        return converter.toRoman(val1 * val2);
    }

    /**
     * Делит первое римское число на второе (целочисленное деление).
     */
    public String divide(String roman1, String roman2) {
        int val1 = converter.toArabic(roman1);
        int val2 = converter.toArabic(roman2);
        if (val2 == 0) {
            throw new ArithmeticException("Деление на ноль.");
        }
        return converter.toRoman(val1 / val2);
    }
}
