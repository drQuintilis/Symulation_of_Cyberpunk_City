package org.example;

public class Main {
    public static void main(String[] args) {

        int population = 100;

        Simulation simulation = new Simulation(population);
        for(int i = 0; i < 1; i++){
            System.out.println("Tick " + (i + 1));
            simulation.doTick();
            simulation.printCitizenInfo();
            System.out.println();
        }
    }
}
