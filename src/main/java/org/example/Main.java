package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;

import java.util.Dictionary;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int tick = 100;

        Simulation simulation = new Simulation(
                100,
                400,
                10,
                2,
                1000,
                0.0001f,
                90,
                20f,
                500,
                100,
                500,
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
                10000
        );
        for(int i = 0; i < tick; i++){
            System.out.println("Tick " + (i + 1));
            simulation.doTick();
            System.out.println();
        }

    }
}
