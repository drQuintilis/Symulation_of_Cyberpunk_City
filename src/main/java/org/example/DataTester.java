package org.example;

public class DataTester {
    public static double[] generateData(EconomicEntity ecEnt, int size){
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = ecEnt.getNextValue();
        }
        return data;
    }
}
