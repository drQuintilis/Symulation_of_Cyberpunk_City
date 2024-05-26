package org.example;

import java.util.Arrays;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import java.io.FileOutputStream;
//import java.io.IOException;

import static org.example.DataTester.generateData; // импорт метода из класса DataTester

public class Simulation {

    public static void main(String[] args) {
        Inequality inequality = new Inequality(0f, 1.2f);
        double[] logNormal = generateData(inequality,10);
        Arrays.sort(logNormal);
        String formattedSample = Arrays.stream(logNormal)
                .mapToObj(number -> String.format("%.2f", number))
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        System.out.println("Generated log-normal sample: " + formattedSample);

        Salary salary = new Salary(100f, 40f); // инициализация класса
        double[] gauss = generateData(salary,10); // вызов метода
        Arrays.sort(gauss); // сортировка по возрастанию
        String formattedGauss = Arrays.stream(gauss) //преобразование в поток
                .mapToObj(number -> String.format("%.2f", number)) // к каждому номеру применяется функция .format()
                .reduce((a, b) -> a + " " + b) // склеивание потока в строку с разделителем
                .orElse(""); // если поток будет пустой, то возвращает пустую строку
        System.out.println("Generated normal sample: " + formattedGauss);
    }
}
// внизу код чтоб данные в эксель засунуть и графики поделать, но нужно закомментить код выше тогда
//public static void main(String[] args) {
//    double[] logNormal = generateLogNormalData(1000, 0f, 0.5f);
//    double[] gauss = generateNormalData(1000, 100f, 50f);
//
//    try (Workbook workbook = new XSSFWorkbook()) {  // Создаем новую книгу Excel
//        Sheet sheet = workbook.createSheet("Samples");  // Создаем новый лист
//
//        // Создаем строки и записываем данные логнормального распределения
//        Row logNormalRow = sheet.createRow(0); // Первая строка
//        for (int i = 0; i < logNormal.length; i++) {
//            Cell cell = logNormalRow.createCell(i);
//            cell.setCellValue(logNormal[i]);
//        }
//
//        // Создаем строки и записываем данные нормального распределения
//        Row gaussRow = sheet.createRow(1); // Вторая строка
//        for (int i = 0; i < gauss.length; i++) {
//            Cell cell = gaussRow.createCell(i);
//            cell.setCellValue(gauss[i]);
//        }
//
//        // Запись файла на диск
//        FileOutputStream outputStream = new FileOutputStream("Data.xlsx");
//        workbook.write(outputStream);
//        outputStream.close();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
