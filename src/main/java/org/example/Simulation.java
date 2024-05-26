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
        ImplantsForCitizens city = new ImplantsForCitizens(10);
        city.addCitizen();
        city.printImplants();
    }
}
