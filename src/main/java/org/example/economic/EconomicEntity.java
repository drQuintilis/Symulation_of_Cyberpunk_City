package org.example.economic;

public interface EconomicEntity { // interfejs dla Salary i Inequality
    public double sigma = 0; // parametr rozkładu: odchylenie standardowe (szerokość dzwonka wartości)
    public double mu = 0; // parametr rozkładu: wartość średnia (przesuwa dzwon wartości wzdłuż osi OX)
    public double getNextValue();
}
