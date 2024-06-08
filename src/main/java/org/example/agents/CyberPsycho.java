package org.example.agents;

import org.example.Simulation;

public class CyberPsycho extends Agent{
    private int strength;
    public CyberPsycho(Simulation simulation, int agentID, int strength) {
        super(agentID, simulation);
        this.strength = strength;
    }

    public String toString(){
        return "CyberPsycho ID: " + this.agentID + "\n" +
                "Strenght :" + this.strength;
    }
}
