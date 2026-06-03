#!/usr/bin/env python3
# generator/gen_tests.py
import yaml
import argparse
import os
from pathlib import Path

# === ШАБЛОНЫ КОДА ДЛЯ JAVA / JUNIT 5 ===
TEST_FILE_TEMPLATE = """//
// AUTO-GENERATED TESTS. DO NOT EDIT MANUALLY.
// Source: {spec_source}
// Generator: gen_tests.py v1.0
package lab.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import lab.implementations.gencode1.RomanNumeralConverter;

@DisplayName("Автоматически сгенерированные тесты для {module_name}")
public class {module_name}GeneratedTests {{

    // Инициализация тестируемой системы (SUT)
    private final RomanNumeralConverter _sut = new RomanNumeralConverter();

{test_methods}
}}
"""

TEST_METHOD_TEMPLATE = """
    @Test
    @DisplayName("Класс эквивалентности: {case_desc}")
    public void test_{method_name}_{case_name}() {{
        // === Arrange ===
        // Предусловие: {pre}
        // Ожидаемый результат: {expected}
        
        // === Act ===
        {act_code}
        
        // === Assert ===
        // Постусловие: {post}
        {assert_code}
    }}
"""

def format_java_input(value):
    if value is None:
        return "null"
    if isinstance(value, str):
        return f'"{value}"'
    if isinstance(value, bool):
        return "true" if value else "false"
    return str(value)

def generate_method_tests(method_data):
    case_blocks = []
    for eq_class in method_data.get("equivalence_classes", []):
        inputs_str = ", ".join(format_java_input(inp) for inp in eq_class["inputs"])
        method_name = method_data["name"]
        expected = eq_class["expected"]
        
        # Определяем, ожидается ли исключение
        is_exception = ".class" in expected or "Exception" in expected
        
        # Act code и Assert code в зависимости от ожидаемого результата
        if is_exception:
            # Извлекаем имя класса исключения
            exception_class = expected.replace(".class", "").strip()
            act_code = f"// Ожидается исключение: {exception_class}"
            assert_code = f"assertThrows({exception_class}, () -> _sut.{method_name}({inputs_str}));"
        else:
            # Обычный тест с проверкой результата
            if "void" in method_data["signature"]:
                act_code = f"_sut.{method_name}({inputs_str});"
                assert_code = 'assertTrue(true, "Метод выполнен успешно без исключений.");'
            else:
                # Определяем тип возвращаемого значения из сигнатуры
                signature = method_data["signature"]
                # Извлекаем тип возвращаемого значения (первое слово в сигнатуре)
                result_type = signature.split()[0]
                
                act_code = f"{result_type} result = _sut.{method_name}({inputs_str});"
                
                # Генерируем правильный assert на основе expected
                if expected.startswith('"') and expected.endswith('"'):
                    # Строковое значение
                    assert_code = f"assertEquals({expected}, result);"
                else:
                    # Числовое значение
                    assert_code = f"assertEquals({expected}, result);"

        safe_case_name = eq_class["case"].replace(" ", "_").replace("(", "").replace(")", "").replace("-", "_")

        case_blocks.append(
            TEST_METHOD_TEMPLATE.format(
                case_desc=eq_class["case"],
                method_name=method_name,
                case_name=safe_case_name,
                pre=method_data["pre"],
                expected=eq_class["expected"],
                act_code=act_code,
                post=method_data["post"],
                assert_code=assert_code
            )
        )
    return case_blocks

def render_and_save(spec, config):
    module_name = spec["module"]
    test_methods = []
    
    for method in spec["methods"]:
        test_methods.extend(generate_method_tests(method))
        
    tests_block = "\n".join(test_methods)
    
    file_content = TEST_FILE_TEMPLATE.format(
        spec_source=config.get("spec_path", "N/A"),
        module_name=module_name,
        test_methods=tests_block
    )
    
    out_dir = Path(config.get("output_dir", "src/lab/tests"))
    out_dir.mkdir(parents=True, exist_ok=True)
    
    output_file = out_dir / f"{module_name}GeneratedTests.java"
    output_file.write_text(file_content, encoding="utf-8")
    
    print(f"[v] Сгенерирован файл: {output_file}")
    print(f"    Методов покрыто: {len(spec['methods'])}")
    total_tests = sum(len(m.get('equivalence_classes', [])) for m in spec['methods'])
    print(f"    Тестов сгенерировано: {total_tests}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--config", default="config.yaml")
    args = parser.parse_args()
    
    with open(args.config, "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)
        
    spec_data = load_spec = yaml.safe_load(open(config["spec_path"], "r", encoding="utf-8"))
    render_and_save(spec_data, config)
    print("[v] Готово.")