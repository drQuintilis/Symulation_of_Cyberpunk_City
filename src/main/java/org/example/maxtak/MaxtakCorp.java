package org.example.maxtak;

import org.example.CitySquare;
import org.example.TickSteps;
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
    public Simulation simulation;
    private List<Integer> activePsychoSquares;


    public MaxtakCorp(Simulation simulation, double moneyFlow, double costOfActiveUnit, double costOfHireUnit){
        this.moneyFlow = moneyFlow;
        this.costOfActiveUnit = costOfActiveUnit;
        this.costOfHireUnit = costOfHireUnit;
        this.savedAmount = 0;
        this.maxtakAgentList = new LinkedList<>();
        this.simulation = simulation;
        this.activePsychoSquares = new LinkedList<Integer>();
    }

    public void doIncomeUpdate() {
        this.savedAmount += this.moneyFlow;
        this.savedAmount -= this.costOfActiveUnit * this.maxtakAgentList.size();
        System.out.println("Maxtak Corp get " + this.moneyFlow + " of income and paid "+ this.costOfActiveUnit * this.maxtakAgentList.size()+ " of salary. Left " + this.savedAmount);
    }

    public void doUpdateAgents(){
        for(int i = 0; ; i++){
            if(this.savedAmount > this.costOfHireUnit && this.moneyFlow
                    > (this.costOfActiveUnit * (this.maxtakAgentList.size() + 1))){
                MaxtakAgent newAgent = new MaxtakAgent(this.simulation, this.simulation.getRandomCitySqaureForAgent(), this.simulation.getAgentId());
                this.maxtakAgentList.add(newAgent);
                this.savedAmount -= this.costOfHireUnit;
                System.out.println("Created new MaxtakAgent with ID:" + newAgent.agentID + " on square: " + newAgent.getSquareId());
            } else break;
        }
    }

    public void callThePolice(int squareId) {
        if (!this.activePsychoSquares.contains(squareId)) {
            this.activePsychoSquares.add(squareId);
            System.out.println("Maxtak Corp received call on: " + squareId);
        }
    }

    public Integer[] getActivePsychoSquares() {
        return activePsychoSquares.toArray(new Integer[0]);
    }

    public void doTick(TickSteps step){
        if (step == TickSteps.ECONOMICS_UPDATE) {
            doIncomeUpdate();
            doUpdateAgents();
        }
    }

    public void deregisterAgent(MaxtakAgent agent){
        this.maxtakAgentList.remove(agent);
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

    public void deregisterCall(int squareID) {
        if (this.activePsychoSquares.contains(squareID)) {
            this.activePsychoSquares.remove((Integer)squareID);
            System.out.println("Maxtak Corp deregister call on: " + squareID);
        }
    }
}
