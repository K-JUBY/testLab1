package lab.gui;

import lab.implementations.gencode1.RomanNumeralConverter;
import lab.interfaces.IRomanNumeralConverter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI приложение для конвертации римских и арабских чисел
 * Использует лучшую реализацию GenCode1 из лабораторной работы №1
 */
public class RomanNumeralConverterGUI extends JFrame {
    
    private final IRomanNumeralConverter converter;
    
    // Компоненты интерфейса
    private JTextField arabicInput;
    private JTextField romanInput;
    private JTextArea resultArea;
    private JTextArea historyArea;
    private JLabel statusLabel;
    
    // История конвертаций
    private List<String> conversionHistory;
    private int conversionCount;
    
    public RomanNumeralConverterGUI() {
        // Используем GenCode1 как лучшую реализацию
        this.converter = new RomanNumeralConverter();
        this.conversionHistory = new ArrayList<>();
        this.conversionCount = 0;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Конвертер римских чисел - Лабораторная работа №7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Панель ввода
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Панель результатов
        JPanel resultPanel = createResultPanel();
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        
        // Панель истории
        JPanel historyPanel = createHistoryPanel();
        mainPanel.add(historyPanel, BorderLayout.EAST);
        
        // Панель статуса
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        updateStatus("Готов к работе");
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(new TitledBorder("Ввод данных"));
        
        // Панель для арабских чисел
        JPanel arabicPanel = new JPanel(new BorderLayout(5, 5));
        arabicPanel.add(new JLabel("Арабское число (1-3999):"), BorderLayout.WEST);
        arabicInput = new JTextField();
        arabicPanel.add(arabicInput, BorderLayout.CENTER);
        JButton toRomanBtn = new JButton("→ В римское");
        toRomanBtn.addActionListener(e -> convertToRoman());
        arabicPanel.add(toRomanBtn, BorderLayout.EAST);
        
        // Панель для римских чисел
        JPanel romanPanel = new JPanel(new BorderLayout(5, 5));
        romanPanel.add(new JLabel("Римское число:"), BorderLayout.WEST);
        romanInput = new JTextField();
        romanPanel.add(romanInput, BorderLayout.CENTER);
        JButton toArabicBtn = new JButton("→ В арабское");
        toArabicBtn.addActionListener(e -> convertToArabic());
        romanPanel.add(toArabicBtn, BorderLayout.EAST);
        
        // Панель кнопок управления
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton clearBtn = new JButton("Очистить");
        clearBtn.addActionListener(e -> clearFields());
        JButton clearHistoryBtn = new JButton("Очистить историю");
        clearHistoryBtn.addActionListener(e -> clearHistory());
        controlPanel.add(clearBtn);
        controlPanel.add(clearHistoryBtn);
        
        panel.add(arabicPanel);
        panel.add(romanPanel);
        panel.add(controlPanel);
        
        return panel;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("Результат"));
        
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("История конвертаций"));
        panel.setPreferredSize(new Dimension(200, 0));
        
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        statusLabel = new JLabel("Готов к работе");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(statusLabel, BorderLayout.WEST);
        
        JLabel infoLabel = new JLabel("Использует реализацию: GenCode1 (Kimi K2)");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void convertToRoman() {
        String input = arabicInput.getText().trim();
        
        if (input.isEmpty()) {
            showError("Введите арабское число");
            return;
        }
        
        try {
            int number = Integer.parseInt(input);
            String roman = converter.toRoman(number);
            
            conversionCount++;
            String result = String.format("Конвертация #%d\n", conversionCount);
            result += String.format("Арабское: %d\n", number);
            result += String.format("Римское: %s\n", roman);
            result += String.format("Статус: ✓ Успешно\n");
            
            resultArea.setText(result);
            romanInput.setText(roman);
            
            addToHistory(String.format("%d → %s", number, roman));
            updateStatus(String.format("Конвертация выполнена успешно (всего: %d)", conversionCount));
            
        } catch (NumberFormatException e) {
            showError("Некорректный формат числа: " + input);
        } catch (IllegalArgumentException e) {
            showError("Ошибка: " + e.getMessage());
        }
    }
    
    private void convertToArabic() {
        String input = romanInput.getText().trim();
        
        if (input.isEmpty()) {
            showError("Введите римское число");
            return;
        }
        
        try {
            int arabic = converter.toArabic(input);
            
            conversionCount++;
            String result = String.format("Конвертация #%d\n", conversionCount);
            result += String.format("Римское: %s\n", input);
            result += String.format("Арабское: %d\n", arabic);
            result += String.format("Статус: ✓ Успешно\n");
            
            resultArea.setText(result);
            arabicInput.setText(String.valueOf(arabic));
            
            addToHistory(String.format("%s → %d", input, arabic));
            updateStatus(String.format("Конвертация выполнена успешно (всего: %d)", conversionCount));
            
        } catch (IllegalArgumentException e) {
            showError("Ошибка: " + e.getMessage());
        }
    }
    
    private void clearFields() {
        arabicInput.setText("");
        romanInput.setText("");
        resultArea.setText("");
        updateStatus("Поля очищены");
    }
    
    private void clearHistory() {
        conversionHistory.clear();
        historyArea.setText("");
        conversionCount = 0;
        updateStatus("История очищена");
    }
    
    private void addToHistory(String entry) {
        conversionHistory.add(entry);
        updateHistoryDisplay();
    }
    
    private void updateHistoryDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = conversionHistory.size() - 1; i >= 0; i--) {
            sb.append(String.format("%d. %s\n", conversionHistory.size() - i, conversionHistory.get(i)));
        }
        historyArea.setText(sb.toString());
    }
    
    private void showError(String message) {
        conversionCount++;
        String result = String.format("Конвертация #%d\n", conversionCount);
        result += String.format("Статус: ✗ Ошибка\n");
        result += String.format("Сообщение: %s\n", message);
        
        resultArea.setText(result);
        updateStatus("Ошибка: " + message);
        
        JOptionPane.showMessageDialog(this, message, "Ошибка ввода", 
                                      JOptionPane.WARNING_MESSAGE);
    }
    
    private void updateStatus(String status) {
        statusLabel.setText("Статус: " + status);
    }
    
    public static void main(String[] args) {
        // Устанавливаем Look and Feel системы
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Запускаем GUI в потоке событий Swing
        SwingUtilities.invokeLater(() -> {
            RomanNumeralConverterGUI gui = new RomanNumeralConverterGUI();
            gui.setVisible(true);
        });
    }
}
