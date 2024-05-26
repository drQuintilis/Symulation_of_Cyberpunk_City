package org.example;

import java.util.Arrays;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import static org.example.Inequality.generateLogNormalData;
import static org.example.Salary.generateNormalData;

public class Simulation {
    public static void main(String[] args) {
        double[] logNormal = generateLogNormalData(10, 0f, 3f);
        Arrays.sort(logNormal);
        String formattedSample = Arrays.stream(logNormal)
                .mapToObj(number -> String.format("%.2f", number))
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        System.out.println("Generated log-normal sample: " + formattedSample);

        double[] gauss = generateNormalData(10, 100f, 50f);
        Arrays.sort(gauss);
        String formattedGauss = Arrays.stream(gauss)
                .mapToObj(number -> String.format("%.2f", number))
                .reduce((a, b) -> a + " " + b)
                .orElse("");
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
