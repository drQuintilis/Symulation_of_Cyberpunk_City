package org.example;
import java.util.Random;
public class Salary {

    private Salary(){}

    public static double[] generateNormalData(int size, double mu, double sigma) {
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            double normalValue = mu + sigma * random.nextGaussian();
            data[i] = normalValue;
        }
        return data;
    }
}
