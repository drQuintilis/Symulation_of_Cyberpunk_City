package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.implants.Implant;

public abstract class Agent {
    public int agentID;
    protected CitySquare position;
    protected Simulation currentSimulation;
    protected boolean isDead;

    public Agent(int agentID, Simulation currentSimulation, CitySquare citySquare){ // konstruktor klasy agent
        this.agentID = agentID;
        this.position = citySquare;
        this.position.registerAgent(this);
        this.currentSimulation = currentSimulation;
        this.currentSimulation.registerAgent(this);
        this.isDead = false;
    }

    public void die(){
        if (this.isDead) return;
        this.isDead = true; // agent umiera
        this.currentSimulation.deRegisterAgent(this); // usuwanie agenta z listy obecnych agentów w symulacji z powodu śmierci
        this.position.deregisterAgent(this); // usuwanie agenta z dzielnicy
        System.out.println(this.getClass().getSimpleName()+ " ID: " + this.agentID + " has died and been deregistered from the simulation and position.");
    }

    public void confirmMove(CitySquare newPosition) {
        this.position = newPosition; // potwierdza poruszanie sie do nowej dzielnicy, po czym agent wie o tym, że jest w nowej dzielnice
    };

    protected void doMovement() {}

    public void doTick(TickSteps step){}

    public String toString() {
        String dead;
        if(isDead) dead = "is dead";
        else dead = "is not dead";
        return "Agent ID: " + this.agentID + " " + dead;
    }

    public int getSquareId() {
        return this.position.squareID;
    }
}
