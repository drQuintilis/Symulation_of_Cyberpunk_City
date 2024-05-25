package org.example;
import org.apache.commons.math3.distribution.LogNormalDistribution;

public class Inequality {
    public static float LogNormal(float multiplierSigma, int multiplierMu) {
        // Создание объекта логонормального распределения с параметрами scale (масштаб) и shape (форма)
        multiplierSigma = 0.0f; // Математическое ожидание логарифма
        multiplierMu = 1; // Стандартное отклонение логарифма
        LogNormalDistribution logNormal = new LogNormalDistribution(multiplierSigma, multiplierMu);

        // Примеры использования методов
        double density = logNormal.density(1.5); // Плотность распределения в точке 1.5
        double cumulativeProbability = logNormal.cumulativeProbability(1.5); // Кумулятивная вероятность до точки 1.5
        double inverseCumulativeProbability = logNormal.inverseCumulativeProbability(0.95); // Обратная кумулятивная вероятность

        System.out.println("Плотность: " + density);
        System.out.println("Кумулятивная вероятность: " + cumulativeProbability);
        System.out.println("95-й перцентиль: " + inverseCumulativeProbability);
        return 0.0f;
    }
}
