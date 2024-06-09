package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

public class CitySquare {

    public Integer squareID;
    public City cityUplink;
    public List<CitySquare> citySquareLinks;
    public List<Agent> agentsOnThisSquare;

    //private Dictionary[] movementRequest;

    public CitySquare(Integer squareID, City cityUplink){
        this.agentsOnThisSquare = new LinkedList<>();
        this.citySquareLinks = new LinkedList<>();
        this.squareID = squareID;
        this.cityUplink = cityUplink;
    }

    public void registerAgent(Agent agent){
        if (agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.add(agent);
    }

    public void deregisterAgent(Agent agent){
        if (agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.remove(agent);
    }

    public void connectSquare(CitySquare square) {
        if (this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.add(square);
        square.connectSquare(this);
    }

    public void disconnectSquare(CitySquare square) {
        if (!this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.remove(square);
        square.disconnectSquare(this);
    }

    public void requestMovement(){

    }

    public void doAgentMoves(){

    }

    public void receiveAgents(Agent agent){

    }

    @Override
    public String toString() {
        LinkedList<Integer> arrayOfSquares = new LinkedList<Integer>();
        for(CitySquare square: this.citySquareLinks){
            if(square != null){
                arrayOfSquares.add(square.squareID);
            }
        }
        return "CitySquare{" +
                "\n  squareID=" + squareID +
                ",\n  citySquareLinks=" + arrayOfSquares +
                ",\n  agentsOnThisSquare=" + agentsOnThisSquare +
                "\n}\n";
    }
}
