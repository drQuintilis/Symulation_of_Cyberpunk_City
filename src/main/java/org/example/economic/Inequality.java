package org.example.economic;
//import org.apache.commons.math3.distribution.LogNormalDistribution;
import java.util.Random;
/**
 * Reprezentuje jednostkę ekonomiczną opartą na rozkładzie log-normalnym do modelowania współczynnika bogactwa.
 */
public class Inequality implements EconomicEntity {

    double muLog;
    double sigmaLog;
    private Random random;
    /**
     * Konstruktor klasy Inequality.
     *<p>
     * @param muLog parametr rozkładu log-normalnego, średnia logarytmiczna
     * @param sigmaLog parametr rozkładu log-normalnego, odchylenie standardowe logarytmiczne
     */
    public Inequality(double muLog, double sigmaLog) { // konstruktor klasy Inequality
        this.muLog = muLog;
        this.sigmaLog = sigmaLog;
        this.random = new Random();
    }
    /**
     * @return następna losowa wartość z rozkładu log-normalnego
     */
    public double getNextValue() { //returns next random value (log normal distribution)
        return Math.exp(this.random.nextGaussian(this.muLog, this.sigmaLog));
    } //rozkład logo-normalny dla otrzymania współczynnika bogactwa
}
