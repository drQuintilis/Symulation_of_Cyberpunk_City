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
    public CyberPsycho(Simulation simulation, CitySquare citySquare,  int agentID, int strength) { //konstruktor klasy cyberpsycho
        super(agentID, simulation, citySquare);
        this.strength = strength;
    }
    private int damage;

    public void attackEveryone(){ // określa, ile osób w dzielnice psycho może zaatakować
        Agent[] agentsOnThisSquare = this.position.getAttackableAgents();
        for (int i = 0; i < strength; i++) {
            agentsOnThisSquare[(int)(Math.random() * agentsOnThisSquare.length)].die(); // wybiera przypadkowo kilka osób (zależy od siły psycho), których zabije
        }
        if (agentsOnThisSquare.length < strength) {
            shouldIMove = true; // jeśli w dzielnice zostaje się za mało osób, których można zabić, to psycho idzie do innej dzielnicy
        }
    }

    protected void doMovement() {
        if (shouldIMove) {
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[ // idzie do dowolnej sąsiedniej dzielnicy
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
        if (this.isDead) return; // jeśli psycho umiera to nic się nie dzieje
        if (attStrength == -1 ) damage++; // maxtak zawsze robi damage o 1
        else damage+= (int) (attStrength/strength); // solo robi damage o sile = ilości jego aktualnych implantów, podzieloną przez siłę psycho
        if (damage >= strength) this.die(); // jeśli damage jest większy od siły psycho, co w tym przypadku także jest HP, to psycho umiera
    }

    public void doTick(TickSteps step){
        if(!this.isDead) {
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement(); // prosi o poruszanie sie do innej dzielnicy
            if (step == TickSteps.CYBER_PSYCHO_ATTACK) attackEveryone(); // tick odpowiadający za atakowanie wszystkich
        }
    }
}
