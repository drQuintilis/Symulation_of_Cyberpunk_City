package org.example.economic;
/**
 * Interfejs dla klas Salary i Inequality, reprezentujący jednostkę ekonomiczną.
 * <p>
 * Zawiera parametry rozkładu i metodę do uzyskania kolejnej wartości z tego rozkładu.
 */
public interface EconomicEntity { // interfejs dla Salary i Inequality
    public double sigma = 0; // parametr rozkładu: odchylenie standardowe (szerokość dzwonka wartości)
    public double mu = 0; // parametr rozkładu: wartość średnia (przesuwa dzwon wartości wzdłuż osi OX)
    /**
     * @return kolejna wartość z rozkładu
     */
    public double getNextValue();
}
