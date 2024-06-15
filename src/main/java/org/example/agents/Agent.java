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

    /**
     * konstruktor klasy
     * @param agentID numer ID agenta
     * @param currentSimulation aktywna symulacja
     * @param citySquare klasa dzielnicy planszy
     */
    public Agent(int agentID, Simulation currentSimulation, CitySquare citySquare){ // konstruktor klasy agent
        this.agentID = agentID;
        this.position = citySquare;
        this.position.registerAgent(this);
        this.currentSimulation = currentSimulation;
        this.currentSimulation.registerAgent(this);
        this.isDead = false;
    }

    /**
     * funkcja die() usuwa agenta z listy obecnych agentów w symulacji z powodu śmierci i usuwa agenta z dzielnicy
     */
    public void die(){
        if (this.isDead) return;
        this.isDead = true; // agent umiera
        this.currentSimulation.deRegisterAgent(this); // usuwanie agenta z listy obecnych agentów w symulacji z powodu śmierci
        this.position.deregisterAgent(this); // usuwanie agenta z dzielnicy
        System.out.println(this.getClass().getSimpleName()+ " ID: " + this.agentID + " has died and been deregistered from the simulation and position.");
    }

    /**
     * funkcja confirmMove() potwierdza poruszanie sie do nowej dzielnicy, po czym agent wie o tym, że jest w nowej dzielnice
     * @param newPosition Obiekt typu CitySquare, reprezentujący nową dzielnicę, do której agent ma się przenieść.
     */
    public void confirmMove(CitySquare newPosition) {
        this.position = newPosition; // potwierdza poruszanie sie do nowej dzielnicy, po czym agent wie o tym, że jest w nowej dzielnice
    };

    /**
     * funkcja doMovement() wykonuje przejścia agentów
     */
    protected void doMovement() {}

    /**
     * Metoda doTick() wykonuje pojedynczy krok agenta.
     *
     * Ta metoda jest wywoływana w celu zaktualizowania stanu agenta
     * w danym kroku symulacji. Kolejność wykonania działań jest określana
     * przez parametr step.
     * @param step kolejność wykonania działań
     */
    public void doTick(TickSteps step){}

    /**
     *Zwraca opis agenta w postaci Stringa.
     * @return String zawierający numer agenta oraz informację, czy agent jest martwy czy żywy.
     */
    public String toString() {
        String dead;
        if(isDead) dead = "is dead";
        else dead = "is not dead";
        return "Agent ID: " + this.agentID + " " + dead;
    }

    /**
     * Metoda zwraca numer "dzielnicy" dla aktualnej pozycji.
     * @return numer "dzielnicy" jako wartość całkowitą.
     */
    public int getSquareId() {
        return this.position.squareID;
    }
}
