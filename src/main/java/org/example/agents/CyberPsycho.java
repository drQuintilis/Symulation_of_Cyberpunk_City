package org.example.agents;
import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CyberPsycho extends Agent{
    private int strength;
    private List<Agent> agentsOnThisSquare;
    private boolean shouldIMove;
    public CyberPsycho(Simulation simulation, CitySquare citySquare,  int agentID, int strength) {
        super(agentID, simulation, citySquare);
        this.strength = strength;
    }
    private int damage;

    public void attackEveryone(){ //defines how many people in the square Psycho can attack
        Agent[] agentsOnThisSquare = this.position.getAttackableAgents();
        for (int i = 0; i < strength; i++) {
            agentsOnThisSquare[(int)(Math.random() * agentsOnThisSquare.length)].die();
        }
        if (agentsOnThisSquare.length < strength) {
            shouldIMove = true;
        }
    }

    protected void doMovement() {
        if (shouldIMove) {
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[
                            (int)(Math.random()*neighbourSquares.length)
                            ].squareID
            );
        }
    }

    public String toString(){
        return "CyberPsycho ID: " + this.agentID + "\n" +
                "Strenght :" + this.strength;
    }

    public void attack(int attStrength) {
        if (this.isDead) return;
        if (attStrength == -1 ) damage++;
        else damage+= (int) (attStrength/strength);
        if (damage > strength) this.die();
    }

    public void doTick(TickSteps step){
        if(!this.isDead) {
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement();
            if (step == TickSteps.CYBER_PSYCHO_ATTACK) attackEveryone();
        }
    }
}
