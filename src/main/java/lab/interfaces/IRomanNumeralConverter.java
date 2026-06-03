package lab.interfaces;

public interface IRomanNumeralConverter {
    // Конвертирует целочисленное значение в римское представление
    String toRoman(int number);

    // Парсит строку с римским числом и возвращает целочисленное значение
    int toArabic(String roman);
}