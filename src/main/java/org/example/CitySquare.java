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
    public boolean isPsychoHere;

    //private Dictionary[] movementRequest;

    public CitySquare(Integer squareID, City cityUplink){
        this.agentsOnThisSquare = new LinkedList<>();
        this.citySquareLinks = new LinkedList<>();
        this.isPsychoHere = false;
        this.squareID = squareID;
        this.cityUplink = cityUplink;
    }

    public void registerAgent(Agent agent){
        this.agentsOnThisSquare.add(agent);
    }

    public void deRegisterAgent(Agent agent){
        this.agentsOnThisSquare.remove(agent);
    }

    public void requestMovement(){

    }

    public void doAgentMoves(){

    }

    public void receiveAgents(Agent agent){

    }

}
