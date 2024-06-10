package org.example.agents;
import org.example.CitySquare;
import org.example.Simulation;
import java.util.LinkedList;
import java.util.List;

public class CyberPsycho extends Agent{
    private int strength;
    private List<Agent> agentsOnThisSquare;
    public CyberPsycho(Simulation simulation, CitySquare citySquare,  int agentID, int strength) {
        super(agentID, simulation, citySquare);
        this.strength = strength;
        this.agentsOnThisSquare = new LinkedList<>(citySquare.agentsOnThisSquare);
    }

    public void attackEveryone(){ //defines how many people in the square Psycho can attack

    }
    public String toString(){
        return "CyberPsycho ID: " + this.agentID + "\n" +
                "Strenght :" + this.strength;
    }
}
