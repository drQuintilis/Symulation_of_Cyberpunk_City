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
        ImplantsForCitizens city = new ImplantsForCitizens(10);
        city.addCitizen();
        city.printImplants();
    }
}
