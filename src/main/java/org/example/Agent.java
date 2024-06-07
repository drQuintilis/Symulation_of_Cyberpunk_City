package org.example;

public abstract class Agent {
    public int agentID;
//    private CitySquare position;
//    private Simulation currentSimulation;

    public Agent(int agentID){
        this.agentID = agentID;
//      this.position = new CitySquare();
//      this.currentSimulation = new Simulation();
    }

//    public void doMovement() {}
    public void die(int agentID) {

    }
}
