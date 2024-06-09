package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.maxtak.MaxtakCorp;

public class MaxtakAgent extends Agent {

    private MaxtakCorp maxtakCorp;
    public MaxtakAgent(Simulation simulation, CitySquare citySquare, int agentID) {
        super(agentID, simulation, citySquare);
//        this.maxtakCorp = new MaxtakCorp();
    }


}
