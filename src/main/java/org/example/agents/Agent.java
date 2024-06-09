package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.implants.Implant;

public abstract class Agent {
    public int agentID;
    protected CitySquare position;
    protected Simulation currentSimulation;
    protected boolean isDead;

    public Agent(int agentID, Simulation currentSimulation, CitySquare citySquare){
        this.agentID = agentID;
        this.position = citySquare;
        this.currentSimulation = currentSimulation;
        this.currentSimulation.registerAgent(this);
        this.isDead = false;
    }

//    public void doMovement() {}
    public void die(){
        this.isDead = true;
        this.currentSimulation.deRegisterAgent(this);
    }

    public void confirmMove(CitySquare newPosition) {
        this.position = newPosition;
    };

    private void doMovement() {

    }

    public void doTick(){}

    public String toString() {
        String dead;
        if(isDead) dead = "is dead";
        else dead = "is not dead";
        return "Agent ID: " + this.agentID + " " + dead;
    }
}
