package org.example;

public class CyberPsycho extends Agent{
    private int strength;
    public CyberPsycho(Simulation simulation, int agentID, int strength) {
        super(agentID, simulation);
        this.strength = strength;
    }
}
