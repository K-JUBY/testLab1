package lab.tests.integration;

import lab.interfaces.IRomanNumeralConverter;
import lab.services.RomanNumeralCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorIntegrationTests {

    /**
     * Поставщик реализаций конвертера для параметризации.
     * Возвращает экземпляры всех трех AI-реализаций.
     */
    static Stream<Arguments> converterProvider() {
        return Stream.of(
            Arguments.of(new lab.implementations.gencode1.RomanNumeralConverter(), "GenCode1 (Kimi K2)"),
            Arguments.of(new lab.implementations.gencode2.RomanNumeralConverter(), "GenCode2"),
            Arguments.of(new lab.implementations.gencode3.RomanNumeralConverter(), "GenCode3")
        );
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Поддержка нижнего регистра")
    void testLowerCaseInput(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        // "x" + "v" = "XV"
        assertEquals("XV", calculator.add("x", "v"), "Ошибка обработки нижнего регистра в " + name);
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Сложение валидных чисел")
    void testAdd_ValidInput(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        
        // 10 + 5 = 15
        assertEquals("XV", calculator.add("X", "V"), "Ошибка сложения в " + name);
        // 900 + 90 + 4 = 994
        assertEquals("CMXCIV", calculator.add("CM", "XCIV"), "Ошибка сложения (сложные числа) в " + name);
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Вычитание с проверкой границ")
    void testSubtract_ValidAndInvalid(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        
        // 20 - 5 = 15
        assertEquals("XV", calculator.subtract("XX", "V"), "Ошибка вычитания в " + name);
        
        // Проверка исключения при результате <= 0 (нарушение контракта римских чисел)
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.subtract("V", "V");
        });
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Умножение и деление")
    void testMultiplyAndDivide(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        
        // 10 * 10 = 100
        assertEquals("C", calculator.multiply("X", "X"), "Ошибка умножения в " + name);
        // 100 / 10 = 10
        assertEquals("X", calculator.divide("C", "X"), "Ошибка деления в " + name);
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Проброс исключений при невалидном вводе")
    void testContractViolation_InvalidInput(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        
        // Невалидный формат римского числа
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("ABC", "X");
        });
        
        // Пустая строка
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("", "X");
        });
    }

    @ParameterizedTest(name = "Тестирование {1}")
    @MethodSource("converterProvider")
    @DisplayName("Интеграция: Граничные условия (выход за диапазон 1-3999)")
    void testBoundaryConditions(IRomanNumeralConverter converter, String name) {
        RomanNumeralCalculator calculator = new RomanNumeralCalculator(converter);
        
        // 3000 + 1000 = 4000 (вне диапазона)
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("MMM", "M");
        });
    }
}
