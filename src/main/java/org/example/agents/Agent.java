package org.example.agents;

import org.example.Simulation;

public abstract class Agent {
    public int agentID;
//    private CitySquare position;
    protected Simulation currentSimulation;

    public Agent(int agentID, Simulation currentSimulation){
        this.agentID = agentID;
//      this.position = new CitySquare();
        this.currentSimulation = currentSimulation;
        this.currentSimulation.registerAgent(this);
    }

//    public void doMovement() {}
    public void doTick(){}
}
