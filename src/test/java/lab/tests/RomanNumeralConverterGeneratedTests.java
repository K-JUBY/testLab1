//
// AUTO-GENERATED TESTS. DO NOT EDIT MANUALLY.
// Source: spec/roman_converter.yaml
// Generator: gen_tests.py v1.0
package lab.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import lab.implementations.gencode1.RomanNumeralConverter;

@DisplayName("Автоматически сгенерированные тесты для RomanNumeralConverter")
public class RomanNumeralConverterGeneratedTests {

    // Инициализация тестируемой системы (SUT)
    private final RomanNumeralConverter _sut = new RomanNumeralConverter();


    @Test
    @DisplayName("Класс эквивалентности: Валидное число 1")
    public void test_toRoman_Валидное_число_1() {
        // === Arrange ===
        // Предусловие: number >= 1 && number <= 3999
        // Ожидаемый результат: "I"
        
        // === Act ===
        String result = _sut.toRoman(1);
        
        // === Assert ===
        // Постусловие: Результат - валидная римская строка
        assertEquals("I", result);
    }


    @Test
    @DisplayName("Класс эквивалентности: Валидное число 3999")
    public void test_toRoman_Валидное_число_3999() {
        // === Arrange ===
        // Предусловие: number >= 1 && number <= 3999
        // Ожидаемый результат: "MMMCMXCIX"
        
        // === Act ===
        String result = _sut.toRoman(3999);
        
        // === Assert ===
        // Постусловие: Результат - валидная римская строка
        assertEquals("MMMCMXCIX", result);
    }


    @Test
    @DisplayName("Класс эквивалентности: Ноль")
    public void test_toRoman_Ноль() {
        // === Arrange ===
        // Предусловие: number >= 1 && number <= 3999
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - валидная римская строка
        assertThrows(IllegalArgumentException, () -> _sut.toRoman(0));
    }


    @Test
    @DisplayName("Класс эквивалентности: Отрицательное число")
    public void test_toRoman_Отрицательное_число() {
        // === Arrange ===
        // Предусловие: number >= 1 && number <= 3999
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - валидная римская строка
        assertThrows(IllegalArgumentException, () -> _sut.toRoman(-5));
    }


    @Test
    @DisplayName("Класс эквивалентности: Больше максимума")
    public void test_toRoman_Больше_максимума() {
        // === Arrange ===
        // Предусловие: number >= 1 && number <= 3999
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - валидная римская строка
        assertThrows(IllegalArgumentException, () -> _sut.toRoman(4000));
    }


    @Test
    @DisplayName("Класс эквивалентности: Валидная строка I")
    public void test_toArabic_Валидная_строка_I() {
        // === Arrange ===
        // Предусловие: roman != null && !roman.isEmpty() && roman.matches("^[IVXLCDM]+$")
        // Ожидаемый результат: 1
        
        // === Act ===
        int result = _sut.toArabic("I");
        
        // === Assert ===
        // Постусловие: Результат - число от 1 до 3999
        assertEquals(1, result);
    }


    @Test
    @DisplayName("Класс эквивалентности: Валидная строка MMMCMXCIX")
    public void test_toArabic_Валидная_строка_MMMCMXCIX() {
        // === Arrange ===
        // Предусловие: roman != null && !roman.isEmpty() && roman.matches("^[IVXLCDM]+$")
        // Ожидаемый результат: 3999
        
        // === Act ===
        int result = _sut.toArabic("MMMCMXCIX");
        
        // === Assert ===
        // Постусловие: Результат - число от 1 до 3999
        assertEquals(3999, result);
    }


    @Test
    @DisplayName("Класс эквивалентности: Пустая строка")
    public void test_toArabic_Пустая_строка() {
        // === Arrange ===
        // Предусловие: roman != null && !roman.isEmpty() && roman.matches("^[IVXLCDM]+$")
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - число от 1 до 3999
        assertThrows(IllegalArgumentException, () -> _sut.toArabic(""));
    }


    @Test
    @DisplayName("Класс эквивалентности: Невалидные символы")
    public void test_toArabic_Невалидные_символы() {
        // === Arrange ===
        // Предусловие: roman != null && !roman.isEmpty() && roman.matches("^[IVXLCDM]+$")
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - число от 1 до 3999
        assertThrows(IllegalArgumentException, () -> _sut.toArabic("ABC"));
    }


    @Test
    @DisplayName("Класс эквивалентности: Null")
    public void test_toArabic_Null() {
        // === Arrange ===
        // Предусловие: roman != null && !roman.isEmpty() && roman.matches("^[IVXLCDM]+$")
        // Ожидаемый результат: IllegalArgumentException.class
        
        // === Act ===
        // Ожидается исключение: IllegalArgumentException
        
        // === Assert ===
        // Постусловие: Результат - число от 1 до 3999
        assertThrows(IllegalArgumentException, () -> _sut.toArabic(null));
    }

}
