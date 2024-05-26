package org.example;
import java.util.Random;
public class Salary implements EconomicEntity {

    double mu;
    double sigma;
    private Random random;
    public Salary(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
        this.random = new Random();
    }
    public Salary(double mu, double sigma, long seed) {
        this.mu = mu;
        this.sigma = sigma;
        this.random = new Random(seed);
    }
    public double getNextValue() {
        return this.random.nextGaussian(this.mu, this.sigma);
    }
}
