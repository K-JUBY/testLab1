# Лабораторная работа №1
**Тема:** RomanNumeral Converter - арабские римские числа.
**Цель работы:** Освоить базовые навыки применения модульного тестирования с акцентом на выявление дефектов и оценку качества реализации программных компонентов.

## 1. Техническое задание и промпт

**Исходное описание задачи:**
Необходимо создать класс с двумя методами, не требующий внешних зависимостей, для преобразования целочисленных значений (арабских чисел) в римские и обратно.

**Точный текст промпта:**
Сгенерируй класс RomanNumeralConverter на Java со следующими методами:
1. Метод: toRoman, входные параметры: int number, возвращает: String.
2. Метод: toArabic, входные параметры: String roman, возвращает: int.
Особые условия: метод toRoman должен выбрасывать IllegalArgumentException при значениях меньше 1 или больше 3999. Метод toArabic должен выбрасывать IllegalArgumentException при передаче пустой строки или некорректного формата римского числа.
Класс должен реализовывать интерфейс IRomanNumeralConverter, находящийся в пакете lab.interfaces.
Класс должен находиться в пакете lab.implementations.gencode1.

## 2. Исходный код

Полные реализации были получены от трех различных генеративных ИИ-систем.

**Реализация 1 (Kimi K2):**
```java
package lab.implementations.gencode1;
import lab.interfaces.IRomanNumeralConverter;

public class RomanNumeralConverter implements IRomanNumeralConverter {
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    @Override
    public String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number must be in the range 1–3999 inclusive.");
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
            // Проверка двухсимвольных комбинаций
            if (i + 1 < upper.length()) {
                String two = upper.substring(i, i + 2);
                int idx = indexOf(two, SYMBOLS);
                if (idx != -1) {
                    result += VALUES[idx];
                    i += 2;
                    continue;
                }
            }
            // Проверка одиночных символов
            String one = upper.substring(i, i + 1);
            int idx = indexOf(one, SYMBOLS);
            if (idx == -1) {
                throw new IllegalArgumentException("Invalid Roman numeral: " + roman);
            }
            result += VALUES[idx];
            i++;
        }
        // Валидация каноничной формы числа через обратное преобразование
        if (!toRoman(result).equals(upper)) {
            throw new IllegalArgumentException("Non-canonical Roman numeral: " + roman);
        }
        return result;
    }

    private static int indexOf(String symbol, String[] symbols) {
        for (int j = 0; j < symbols.length; j++) {
            if (symbols[j].equals(symbol)) return j;
        }
        return -1;
    }
}
```

**Реализация 2 (GenCode2):**
```java
package lab.implementations.gencode2;
import lab.interfaces.IRomanNumeralConverter;

public class RomanNumeralConverter implements IRomanNumeralConverter {
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final String ROMAN_REGEX = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    @Override
    public String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Аргумент number должен быть в диапазоне 1..3999 (включительно).");
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
        if (roman == null) throw new IllegalArgumentException("Аргумент roman не должен быть null.");
        String s = roman.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Аргумент roman не должен быть пустой строкой.");
        
        if (!s.matches(ROMAN_REGEX)) {
            throw new IllegalArgumentException("Некорректный формат римского числа: " + roman);
        }

        int total = 0;
        int i = 0;
        while (i < s.length()) {
            if (i + 1 < s.length()) {
                String pair = s.substring(i, i + 2);
                int pairValue = valueOf(pair);
                if (pairValue > 0) {
                    total += pairValue;
                    i += 2;
                    continue;
                }
            }
            String single = s.substring(i, i + 1);
            int singleValue = valueOf(single);
            total += singleValue;
            i++;
        }
        return total;
    }

    private static int valueOf(String symbol) {
        switch (symbol) {
            case "M": return 1000;
            case "CM": return 900;
            case "D": return 500;
            case "CD": return 400;
            case "C": return 100;
            case "XC": return 90;
            case "L": return 50;
            case "XL": return 40;
            case "X": return 10;
            case "IX": return 9;
            case "V": return 5;
            case "IV": return 4;
            case "I": return 1;
            default: return 0;
        }
    }
}
```

**Реализация 3 (GenCode3):**
```java
package lab.implementations.gencode3;
import lab.interfaces.IRomanNumeralConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RomanNumeralConverter implements IRomanNumeralConverter {
    private static final Map<Character, Integer> ROMAN_TO_ARABIC = new HashMap<>();
    private static final int[] ARABIC_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final Pattern VALID_ROMAN_PATTERN = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

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
```

**Краткий анализ различий:** Код от первой модели (Kimi K2) включает валидацию каноничной формы числа. Вторая модель (GenCode2) не осуществляет нормализацию входных данных (отсутствует приведение строки к верхнему регистру). Третья модель предоставляет базовую работоспособную логику.

## 3. Тесты

**Сценарии тестов:**
1. `toRoman_ValidInput_ReturnsRomanString` — Проверяет корректную работу модуля при валидных входных данных. Данные: `10`. Ожидается: `"X"`.
2. `toRoman_BoundaryValues_ReturnsCorrectStrings` — Исследует поведение модуля на предельных значениях входных параметров. Данные: `1`, `3999`. Ожидается: `"I"`, `"MMMCMXCIX"`.
3. `toRoman_OutOfRange_ThrowsIllegalArgumentException` — Явно проверяет выброс ожидаемых исключений в определённых условиях. Данные: `0`, `4000`. Ожидается: `IllegalArgumentException`.
4. `toArabic_ValidInput_ReturnsArabicNumber` — Проверяет корректное преобразование строки в число. Данные: `"MCMXCIV"`. Ожидается: `1994`.
5. `toArabic_NullOrEmptyString_ThrowsIllegalArgumentException` — Проверяет реакцию модуля на пустые входные данные. Данные: `""`, `null`. Ожидается: `IllegalArgumentException`.
6. `toArabic_LowerCaseString_ProcessedSuccessfully` — Проверяет обработку регистра (тест «белого ящика» на основе анализа реализации). Данные: `"xiv"`. Ожидается: `14`.
7. `toArabic_NonCanonicalFormat_ThrowsIllegalArgumentException` — Проверяет защиту от неканоничного ввода. Данные: `"IIII"`. Ожидается: `IllegalArgumentException`.
8. `toArabic_InvalidCharacters_ThrowsIllegalArgumentException` — Проверяет обработку недопустимых символов. Данные: `"X1V"`. Ожидается: `IllegalArgumentException`.

**Код тестов:**
```java
package lab.tests;

import lab.implementations.gencode1.RomanNumeralConverter;
import lab.interfaces.IRomanNumeralConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenCode1Tests {

    private final IRomanNumeralConverter converter = new RomanNumeralConverter();

    // Тесты "черного ящика" (на основе спецификации)
    
    @Test
    public void toRoman_ValidInput_ReturnsRomanString() {
        int number = 10;
        String result = converter.toRoman(number);
        assertEquals("X", result);
    }

    @Test
    public void toRoman_BoundaryValues_ReturnsCorrectStrings() {
        assertEquals("I", converter.toRoman(1));
        assertEquals("MMMCMXCIX", converter.toRoman(3999));
    }

    @Test
    public void toRoman_OutOfRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> converter.toRoman(0));
        assertThrows(IllegalArgumentException.class, () -> converter.toRoman(4000));
    }

    @Test
    public void toArabic_ValidInput_ReturnsArabicNumber() {
        String roman = "MCMXCIV";
        int result = converter.toArabic(roman);
        assertEquals(1994, result);
    }

    @Test
    public void toArabic_NullOrEmptyString_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> converter.toArabic(""));
        assertThrows(IllegalArgumentException.class, () -> converter.toArabic(null));
    }

    // Тесты "белого ящика" (на основе анализа реализации алгоритма)

    @Test
    public void toArabic_LowerCaseString_ProcessedSuccessfully() {
        // Проверка внутренней логики приведения строки к верхнему регистру
        String lowerCaseRoman = "xiv";
        int result = converter.toArabic(lowerCaseRoman);
        assertEquals(14, result);
    }

    @Test
    public void toArabic_NonCanonicalFormat_ThrowsIllegalArgumentException() {
        // Проверка ветвления логики обратного преобразования
        String nonCanonicalRoman = "IIII";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(nonCanonicalRoman);
        });
        assertTrue(exception.getMessage().contains("Non-canonical Roman numeral"));
    }

    @Test
    public void toArabic_InvalidCharacters_ThrowsIllegalArgumentException() {
        // Проверка обработки символов, отсутствующих в массиве констант
        String invalidRoman = "X1V";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(invalidRoman);
        });
        assertTrue(exception.getMessage().contains("Invalid Roman numeral"));
    }
}
```

**Таблица результатов тестирования всех реализаций кода:**

| Название теста (Сценарий) | Тип ящика | GenCode1 (Kimi K2) | GenCode2 | GenCode3 |
| :--- | :--- | :--- | :--- | :--- |
| `toRoman_ValidInput_ReturnsRomanString` | Чёрный | Успешно | Успешно | Успешно |
| `toRoman_BoundaryValues_ReturnsCorrectStrings` | Чёрный | Успешно | Успешно | Успешно |
| `toRoman_OutOfRange_ThrowsIllegalArgumentException` | Чёрный | Успешно | Успешно | Успешно |
| `toArabic_ValidInput_ReturnsArabicNumber` | Чёрный | Успешно | Успешно | Успешно |
| `toArabic_NullOrEmptyString_ThrowsIllegalArgumentException` | Чёрный | Успешно | Успешно | Успешно |
| `toArabic_LowerCaseString_ProcessedSuccessfully` | Белый | Успешно | **Провален** | Успешно |
| `toArabic_NonCanonicalFormat_ThrowsIllegalArgumentException` | Белый | Успешно | **Провален** | **Провален** |
| `toArabic_InvalidCharacters_ThrowsIllegalArgumentException` | Белый | Успешно | **Провален** | **Провален** |

**Анализ причин падения тестов:**
* **GenCode2 (Падение `toArabic_LowerCaseString`):** Ошибка вызвана отсутствием в реализации нормализации входных данных. Алгоритм не находит символы нижнего регистра в массиве констант и выбрасывает исключение.
* **GenCode2 и GenCode3 (Падение `toArabic_NonCanonicalFormat` и `toArabic_InvalidCharacters`):** Технический анализ показывает, что реализации работают корректно. Тесты упали на проверке текста исключений, так как модели сгенерировали сообщения на разных языках, а проверка `assertThrows` ожидала строгое совпадение строк.

## 4. Выводы

* **Оценка качества (1-5) для каждой реализации:** GenCode1 получает **5/5**. GenCode2 получает **3/5**. GenCode3 получает **4/5**.
* **Как тестирование помогло найти ошибки?:** Тесты «белого ящика» выявили скрытый дефект во второй реализации (отсутствие нормализации регистра). Обнаружена проблема излишней жесткости самих тестов при проверке локализованных исключений.
* **Как улучшить промпт?:** В промпт необходимо добавить требование поддержки любого регистра и строгого использования английского языка для генерации исключений.