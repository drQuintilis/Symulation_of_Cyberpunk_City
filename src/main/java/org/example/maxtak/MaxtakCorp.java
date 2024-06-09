package org.example.maxtak;

import org.example.CitySquare;
import org.example.agents.Agent;
import org.example.agents.MaxtakAgent;
import org.example.Simulation;

import java.util.LinkedList;
import java.util.List;

public class MaxtakCorp {
    public double moneyFlow;
    public double costOfActiveUnit;
    public double costOfHireUnit;
    private double savedAmount;
    public List<MaxtakAgent> maxtakAgentList;
    public Agent agent;
    public Simulation simulation;

    public CitySquare position;


    public MaxtakCorp(Simulation simulation, CitySquare citySquare, double moneyFlow, double costOfActiveUnit, double costOfHireUnit){
        this.moneyFlow = moneyFlow;
        this.costOfActiveUnit = costOfActiveUnit;
        this.costOfHireUnit = costOfHireUnit;
        this.savedAmount = 0;
        this.maxtakAgentList = new LinkedList<>();
        this.simulation = simulation;
        this.position = citySquare;
    }

    public void doIncomeUpdate() {
        this.savedAmount += this.moneyFlow;
        this.savedAmount -= this.costOfActiveUnit * this.maxtakAgentList.size();
    }

    public void doUpdateAgents(){
        for(int i = 0; ; i++){
            if(this.savedAmount > this.costOfHireUnit && this.moneyFlow
                    > (this.costOfActiveUnit * (this.maxtakAgentList.size() + 1))){
                this.maxtakAgentList.add(new MaxtakAgent(this.simulation, this.position,this.agent.agentID));
                this.savedAmount -= this.costOfHireUnit;
            } else break;
        }
    }

    public void doTick(String step){
        doIncomeUpdate();
        doUpdateAgents();
    }

    @Override
    public String toString() {
        String arrayOfMaxtakAgents = "";
        for(MaxtakAgent maxtakAgent: this.maxtakAgentList){
            if(maxtakAgent != null){
                arrayOfMaxtakAgents = arrayOfMaxtakAgents + maxtakAgent.toString();
            }
        }
        return "MaxtakCorp date:" + "\n" +
                "Money flow: " + this.moneyFlow + "\n" +
                "Cost of active unit: " + this.costOfActiveUnit +"\n" +
                "Cost of hire unit: " + this.costOfHireUnit +"\n" +
                "Saved amount: " + this.savedAmount + "\n" +
                "Array of Maxtak agents: " + arrayOfMaxtakAgents;
    }
}
