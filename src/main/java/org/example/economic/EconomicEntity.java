package org.example.economic;

public interface EconomicEntity { // interface for Salary and Inequality
    public double sigma = 0; // distribution parameter: standard deviation (affects bell thickness)
    public double mu = 0; // distribution parameter: average value (moves the bell along the OX axis)
    public double getNextValue();
}
