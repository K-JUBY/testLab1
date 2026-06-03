# Лабораторная работа 5: Автоматическая генерация тестов

## Описание проекта

Проект демонстрирует автоматическую генерацию JUnit-тестов на основе формальной YAML-спецификации для модуля `RomanNumeralConverter`.

**Модуль:** Конвертер римских чисел (Roman Numeral Converter)
- Преобразование арабских чисел (1-3999) в римскую запись
- Преобразование римской записи в арабские числа (1-3999)

## Структура проекта

```
testLab1/
├── src/
│   ├── main/java/lab/
│   │   ├── interfaces/IRomanNumeralConverter.java
│   │   └── implementations/gencode1/RomanNumeralConverter.java
│   └── test/java/lab/tests/
│       ├── RomanNumeralConverterGeneratedTests.java (автогенерируемый)
│       └── GenCode1Tests.java
├── spec/
│   └── roman_converter.yaml              # Формальная спецификация
├── generator/
│   └── gen_tests.py                      # Python-генератор тестов
├── .github/workflows/
│   ├── ci.yml                            # CI пайплайн с генерацией тестов
│   └── compare.yml
├── config.yaml                           # Конфигурация генератора
└── pom.xml                               # Maven проект
```

## Требования

- **Python 3.11+** (для генератора тестов)
- **JDK 17** (для компиляции и запуска)
- **Maven 3.x** (для сборки проекта)
- **PyYAML** (для парсинга спецификации)

## Установка зависимостей

### Python зависимости
```bash
pip install pyyaml
```

### Проверка Java и Maven
```bash
java -version
mvn -version
```

## Локальный запуск

### 1. Генерация тестов
```bash
python generator/gen_tests.py --config config.yaml
```

Генератор создаст файл: `src/test/java/lab/tests/RomanNumeralConverterGeneratedTests.java`

### 2. Компиляция и запуск тестов
```bash
# Полная сборка с тестами
mvn clean test

# Только компиляция
mvn compile

# Запуск конкретного теста
mvn test -Dtest=RomanNumeralConverterGeneratedTests
```

## CI/CD Pipeline (GitHub Actions)

Пайплайн автоматически выполняется при:
- Push в ветки: `main`, `lab5-generation`, `ai-version-*`
- Pull request в `main`
- Ручной запуск (`workflow_dispatch`)

### Этапы пайплайна:
1. **Checkout** — получение кода из репозитория
2. **Setup Python** — установка Python 3.11
3. **Install dependencies** — установка PyYAML
4. **Generate tests** — автоматическая генерация тестов из спецификации
5. **Setup JDK** — установка Java 17
6. **Build** — компиляция проекта
7. **Run tests** — запуск всех тестов (включая сгенерированные)
8. **Upload artifacts** — сохранение результатов тестирования

### Артефакты
Результаты тестирования доступны в разделе **Actions → Artifacts**:
- `junit-test-results` — содержит отчёты Surefire

## Формальная спецификация

Файл `spec/roman_converter.yaml` содержит:

### Функциональные требования
- **FR-1**: Преобразование арабского числа в римскую запись
- **FR-2**: Преобразование римской записи в арабское число
- **FR-3**: Валидация входных данных с исключениями

### Нефункциональные требования
- **NFR-1**: Диапазон чисел 1-3999
- **NFR-2**: Запрет недопустимых символов

### Методы и классы эквивалентности

#### `toRoman(int number)`
Классы эквивалентности:
- Валидное число 1 → `"I"`
- Валидное число 3999 → `"MMMCMXCIX"`
- Ноль → `IllegalArgumentException`
- Отрицательное число → `IllegalArgumentException`
- Больше максимума (4000) → `IllegalArgumentException`

#### `toArabic(String roman)`
Классы эквивалентности:
- Валидная строка "I" → `1`
- Валидная строка "MMMCMXCIX" → `3999`
- Пустая строка → `IllegalArgumentException`
- Невалидные символы → `IllegalArgumentException`
- Null → `IllegalArgumentException`

## Генератор тестов

`generator/gen_tests.py` автоматически:
- Парсит YAML-спецификацию
- Преобразует классы эквивалентности в JUnit-тесты
- Генерирует правильные Assert'ы:
  - `assertEquals(expected, result)` для проверки значений
  - `assertThrows(Exception.class, () -> ...)` для проверки исключений
- Определяет типы возвращаемых значений из сигнатуры метода
- Создаёт структуру Arrange-Act-Assert

## Трассировка требований

Каждый метод в спецификации содержит явную привязку:
- `req_functional` — список функциональных требований
- `req_non_functional` — список нефункциональных требований

Пример:
```yaml
- name: toRoman
  req_functional: ["1"]
  req_non_functional: ["NFR-1", "NFR-2", "NFR-3"]
```

## Статистика генерации

При запуске генератора выводится:
```
[✓] Сгенерирован файл: src/test/java/lab/tests/RomanNumeralConverterGeneratedTests.java
    Методов покрыто: 2
    Тестов сгенерировано: 10
```

## Важные замечания

1. **НЕ редактируйте** файл `RomanNumeralConverterGeneratedTests.java` вручную — он помечен как `AUTO-GENERATED` и перезаписывается при каждом запуске генератора.

2. **Детерминированность**: генератор детерминирован — одна и та же спецификация всегда создаёт одинаковые тесты.

3. **Расширение**: для добавления новых тестов редактируйте `spec/roman_converter.yaml`, затем перезапускайте генератор.

## Отчет (для лабораторной работы)

Для подготовки отчета используйте:
- Скриншоты успешного выполнения пайплайна из GitHub Actions
- Листинги файлов: `roman_converter.yaml`, `gen_tests.py`, `ci.yml`
- Примеры сгенерированных тестов с комментариями
- Матрицу трассировки: Требование → YAML-блок → Сгенерированный тест
- Результаты из артефактов: `junit-test-results`

## Автор

Лабораторная работа 5 по курсу "Тестирование программного обеспечения"

**Дата:** Июнь 2026
