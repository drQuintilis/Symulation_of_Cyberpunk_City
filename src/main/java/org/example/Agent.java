package org.example;

public abstract class Agent {
    public int agentID;
//    private CitySquare position;
    protected Simulation currentSimulation;

    public Agent(int agentID, Simulation currentSimulation){
        this.agentID = agentID;
//      this.position = new CitySquare();
        this.currentSimulation = currentSimulation;
    }

//    public void doMovement() {}
    public void doTick(){}
}
