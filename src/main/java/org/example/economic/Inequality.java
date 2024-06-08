package org.example.economic;
//import org.apache.commons.math3.distribution.LogNormalDistribution;
import java.util.Random;

public class Inequality implements EconomicEntity {

    double muLog;
    double sigmaLog;
    private Random random;
    public Inequality(double muLog, double sigmaLog) {
        this.muLog = muLog;
        this.sigmaLog = sigmaLog;
        this.random = new Random();
    }
    public Inequality(double muLog, double sigmaLog, long seed) {
        this.muLog = muLog;
        this.sigmaLog = sigmaLog;
        this.random = new Random(seed);
    }

    public double getNextValue() { // получить слеующее рандомное значение по логонормальному распределению (одно)
        return Math.exp(this.random.nextGaussian(this.muLog, this.sigmaLog));
    }
}
