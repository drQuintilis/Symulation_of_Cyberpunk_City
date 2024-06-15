package org.example.economic;
//import org.apache.commons.math3.distribution.LogNormalDistribution;
import java.util.Random;

public class Inequality implements EconomicEntity {

    double muLog;
    double sigmaLog;
    private Random random;
    public Inequality(double muLog, double sigmaLog) { // konstruktor klasy Inequality
        this.muLog = muLog;
        this.sigmaLog = sigmaLog;
        this.random = new Random();
    }

    public double getNextValue() { //returns next random value (log normal distribution)
        return Math.exp(this.random.nextGaussian(this.muLog, this.sigmaLog));
    } //rozkład logo-normalny dla otrzymania współczynnika bogactwa
}
