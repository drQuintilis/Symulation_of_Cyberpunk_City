package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;

import java.util.Dictionary;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        int population = 1;
        int tick = 100;

        Simulation simulation = new Simulation(
                100,
                400,
                10,
                2,
                10000,
                0.7f,
                20,
                0.5f,
                5000,
                5,
                30,
                new int[][]{ //graf, czyli nasze miasto
                        {3, 5, 7, 9},
                        {4, 5, 7, 8},
                        {1, 5, 6, 9},
                        {2, 5, 6, 8},
                        {1, 2, 3, 4, 6, 7},
                        {3, 4, 5},
                        {1, 2, 5},
                        {2, 4},
                        {1, 3}
                },
                15,
                5,
                10,
                1000
        );
        for(int i = 0; i < tick; i++){
            System.out.println("Tick " + (i + 1));
            simulation.doTick();
            System.out.println();
        }

    }
}
