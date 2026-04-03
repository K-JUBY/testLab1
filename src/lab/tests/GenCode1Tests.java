package lab.tests;

import lab.implementations.gencode2.RomanNumeralConverter;
import lab.interfaces.IRomanNumeralConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenCode1Tests {

    private final IRomanNumeralConverter converter = new RomanNumeralConverter();

    // Тесты "черного ящика" (на основе спецификации)

    @Test
    public void toRoman_ValidInput_ReturnsRomanString() {
        // Arrange: инициализация тестовых данных
        int number = 10;

        // Act: вызов тестируемого метода
        String result = converter.toRoman(number);

        // Assert: проверка ожидаемого результата
        assertEquals("X", result);
    }

    @Test
    public void toRoman_BoundaryValues_ReturnsCorrectStrings() {
        // Проверка граничных значений из диапазона 1-3999
        assertEquals("I", converter.toRoman(1));
        assertEquals("MMMCMXCIX", converter.toRoman(3999));
    }

    @Test
    public void toRoman_OutOfRange_ThrowsIllegalArgumentException() {
        // Act & Assert: вызов метода с некорректными данными и проверка исключения
        assertThrows(IllegalArgumentException.class, () -> {
            converter.toRoman(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            converter.toRoman(4000);
        });
    }

    @Test
    public void toArabic_ValidInput_ReturnsArabicNumber() {
        // Arrange
        String roman = "MCMXCIV"; // 1994

        // Act
        int result = converter.toArabic(roman);

        // Assert
        assertEquals(1994, result);
    }

    @Test
    public void toArabic_NullOrEmptyString_ThrowsIllegalArgumentException() {
        // Act & Assert: проверка реакции модуля на пустые входные данные
        assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(null);
        });
    }

    // Тесты "белого ящика" (на основе анализа реализации Kimi K2)

    @Test
    public void toArabic_LowerCaseString_ProcessedSuccessfully() {
        // Этот сценарий не описан явно в спецификации, но анализ кода показывает
        // использование метода toUpperCase(), что означает поддержку нижнего регистра.

        // Arrange
        String lowerCaseRoman = "xiv"; // 14

        // Act
        int result = converter.toArabic(lowerCaseRoman);

        // Assert
        assertEquals(14, result);
    }

    @Test
    public void toArabic_NonCanonicalFormat_ThrowsIllegalArgumentException() {
        // Анализ кода: в методе toArabic есть проверка обратным преобразованием
        // (!toRoman(result).equals(upper)). Проверяем, что неканоничная запись "IIII"
        // вызовет ошибку, хотя алгоритмически это могло бы быть вычислено как 4.

        // Arrange
        String nonCanonicalRoman = "IIII";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(nonCanonicalRoman);
        });

        // Дополнительная проверка содержимого сообщения об ошибке
        assertTrue(exception.getMessage().contains("Non-canonical Roman numeral"));
    }

    @Test
    public void toArabic_InvalidCharacters_ThrowsIllegalArgumentException() {
        // Анализ кода: если символ не найден в массиве SYMBOLS, генерируется исключение.

        // Arrange
        String invalidRoman = "X1V";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(invalidRoman);
        });

        assertTrue(exception.getMessage().contains("Invalid Roman numeral"));
    }
}