package org.example;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import java.util.Random;

public class Inequality {
    private Inequality() {}

//    public static double logNormal(float multiplierMu, int multiplierSigma) {
//        LogNormalDistribution logNormal = new LogNormalDistribution(multiplierMu, multiplierSigma);
//        return logNormal.sample();
//    }
    public static double[] generateLogNormalData(int size, double meanLog, double sdLog) {
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            double normalValue = meanLog + sdLog * random.nextGaussian();
            data[i] = Math.exp(normalValue);
        }
        return data;
    }
}
