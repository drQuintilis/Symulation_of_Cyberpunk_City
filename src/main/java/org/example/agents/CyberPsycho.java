package org.example.agents;
import org.example.CitySquare;
import org.example.Simulation;

public class CyberPsycho extends Agent{
    private int strength;
    public CyberPsycho(Simulation simulation, CitySquare citySquare,  int agentID, int strength) {
        super(agentID, simulation, citySquare);
        this.strength = strength;
    }

    public void attackEveryone(){

    }
    public String toString(){
        return "CyberPsycho ID: " + this.agentID + "\n" +
                "Strenght :" + this.strength;
    }
}
