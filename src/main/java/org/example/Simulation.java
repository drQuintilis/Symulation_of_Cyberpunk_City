package org.example;

import java.util.Arrays;

import static org.example.Inequality.generateLogNormalData;
//import static org.example.Inequality.logNormal;

public class Simulation {
    public static void main(String[] args) {
//      double sample = logNormal(0.5f, 1); // Задаем среднее и стандартное отклонение для логарифма
        double[] sample = generateLogNormalData(10, 0f, 1);
        Arrays.sort(sample);
        String formattedSample = Arrays.stream(sample)
                                        .mapToObj(number -> String.format("%.3f", number))
                                        .reduce((a, b) -> a + ", " + b)
                                        .orElse("");
        System.out.println("Generated log-normal sample: " + formattedSample);
    }
}