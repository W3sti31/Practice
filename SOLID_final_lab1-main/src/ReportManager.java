import java.io.PrintWriter;

//TIP Задача 1: S (Single Responsibility)
// Проблема: Класс ReportManager делает слишком много:
// считает данные,
// форматирует их в строку и
// записывает в файл.
// ПЛОХО: Если изменится формат файла или логика расчетов, придется править один и тот же класс.

// Задание: Разделите этот класс на три: Calculator, ReportFormatter и FileSaver.
class Calculator {
    public double calculate() {
        return 500.0 * 1.2;
    }
}
class ReportFormatter {
    public String format(double data) {
        return "Отчет: " + data;
    }
}
class FileSaver {
    public void saveToFile(String content, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}