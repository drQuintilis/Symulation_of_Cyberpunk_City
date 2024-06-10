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
    //co to jest i czy my tego potzrebujemy?
//    public Inequality(double muLog, double sigmaLog, long seed) {
//        this.muLog = muLog;
//        this.sigmaLog = sigmaLog;
//        this.random = new Random(seed);
//    }
    public double getNextValue() { //returns next random value (log normal distribution)
        return Math.exp(this.random.nextGaussian(this.muLog, this.sigmaLog));
    }
}
