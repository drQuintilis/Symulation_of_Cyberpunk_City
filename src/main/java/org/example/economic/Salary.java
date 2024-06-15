package org.example.economic;
import java.util.Random;
/**
 * Reprezentuje jednostkę ekonomiczną opartą na rozkładzie normalnym do modelowania wartości pensji.
 */
public class Salary implements EconomicEntity {

    double mu;
    double sigma;
    private Random random;
    /**
     * Konstruktor klasy Salary.
     * <p>
     * @param mu parametr rozkładu normalnego, średnia
     * @param sigma parametr rozkładu normalnego, odchylenie standardowe
     */
    public Salary(double mu, double sigma) { // konstruktor klasy Salary
        this.mu = mu;
        this.sigma = sigma;
        this.random = new Random();
    }

    /**
     * @return następna losowa wartość z rozkładu normalnego
     */
    public double getNextValue() { //returns next random value (normal distribution)
        return this.random.nextGaussian(this.mu, this.sigma);
    } // rozkład normalny dla otrzymania wartości pensji citizena co tick
}
