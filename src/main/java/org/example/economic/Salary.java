package org.example.economic;
import java.util.Random;
public class Salary implements EconomicEntity {

    double mu;
    double sigma;
    private Random random;
    public Salary(double mu, double sigma) { // konstruktor klasy Salary
        this.mu = mu;
        this.sigma = sigma;
        this.random = new Random();
    }
//    public Salary(double mu, double sigma, long seed) { // конструктор второй с заданным значением для рандома,
//        this.mu = mu;                                   // чтобы числа вызывались всегда одинаковые для теста данных
//        this.sigma = sigma;
//        this.random = new Random(seed);
//    }
    public double getNextValue() { //returns next random value (normal distribution)
        return this.random.nextGaussian(this.mu, this.sigma);
    } // rozkład normalny dla otrzymania wartości pensji citizena co tick
}
