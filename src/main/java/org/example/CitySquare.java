package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;
import org.example.agents.CyberPsycho;
import org.example.agents.MaxtakAgent;

import java.util.*;

public class CitySquare {

    public Integer squareID;
    public City cityUplink;
    private List<CitySquare> citySquareLinks;
    public List<Agent> agentsOnThisSquare; //lista agentow
    private List<Map.Entry<Agent, CitySquare>> movementRequests;
    private int throughput;


    public CitySquare(Integer squareID, City cityUplink, int citySize){
        this.agentsOnThisSquare = new LinkedList<>();
        this.citySquareLinks = new ArrayList<>(citySize);
        for (int i = 0; i < citySize; i++) {
            this.citySquareLinks.add(null);
        }
        this.squareID = squareID;
        this.cityUplink = cityUplink;
        this.movementRequests = new ArrayList<Map.Entry<Agent, CitySquare>>();
        this.throughput = 15;
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
        this.citySquareLinks.set(square.squareID, square);
        square.connectSquare(this);
    }

    public void disconnectSquare(CitySquare square) {
        if (!this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.remove(square);
        square.disconnectSquare(this);
    }

    public Agent[] getAttackableAgents() {
        List<Agent> attackableAgents = new ArrayList<Agent>();
        for (Agent agent: this.agentsOnThisSquare) {
            if (agent instanceof Citizen) attackableAgents.add(agent);
        }
        Agent[] retVal = new Agent[attackableAgents.size()];
        attackableAgents.toArray(retVal);
        return retVal;
    }

    public boolean isPsychoHere() {
        for (Agent agent: this.agentsOnThisSquare) {
            if (agent instanceof CyberPsycho) return true;
        }
        return false;
    }

    public void requestMovement(Agent currentAgent, int targetSquare){
        this.movementRequests.add(Map.entry(currentAgent, this.citySquareLinks.get(targetSquare)));
    }

    public void doAgentMoves(){
        if (this.movementRequests.size() == 0) return;
        List<Map.Entry<Agent, CitySquare>> movementsToExecute = new LinkedList<>();
        for (int i = 0; i < this.throughput; i++) {
            Map.Entry<Agent, CitySquare> currentEntry = movementRequests.get((int)(Math.random() * movementRequests.size()));
            if (movementsToExecute.contains(currentEntry)) continue;
            movementsToExecute.add(currentEntry);
        }
        for (Map.Entry<Agent, CitySquare> executingMove:
             movementsToExecute) {
            executingMove.getValue().registerAgent(executingMove.getKey());
            this.deregisterAgent(executingMove.getKey());
            executingMove.getKey().confirmMove(executingMove.getValue());
        }
        this.movementRequests = new ArrayList<>();
    }

    public void doTick(TickSteps step){
        if (step == TickSteps.MOVEMENTS_EXECUTION) doAgentMoves();
    }

    public CitySquare[] getCitySquareLinks() {
        LinkedList<CitySquare> arrayOfSquares = new LinkedList<CitySquare>();
        for(CitySquare square: this.citySquareLinks){
            if(square != null){
                arrayOfSquares.add(square);
            }
        }
        return arrayOfSquares.toArray(new CitySquare[0]);
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

    public CyberPsycho getPsycho() {
        List<CyberPsycho> cyberPsychos = new ArrayList<CyberPsycho>();
        for (Agent agent: this.agentsOnThisSquare) {
            if (agent instanceof CyberPsycho) cyberPsychos.add((CyberPsycho) agent);
        }
        return cyberPsychos.get((int)(Math.random()*cyberPsychos.size()));
    }

}
