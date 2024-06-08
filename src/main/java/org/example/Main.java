package org.example;

public class Main {
    public static void main(String[] args) {

        int population = 1;
        int tick = 10;

        Simulation simulation = new Simulation(population);
        for(int i = 0; i < tick; i++){
            System.out.println("Tick " + (i + 1));
            simulation.doTick();
            simulation.printCitizenInfo();
            System.out.println();
        }
    }
}
