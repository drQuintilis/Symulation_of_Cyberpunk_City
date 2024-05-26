package org.example;

import static org.example.EconomicEntity.*;
public class Citizen extends Agent {
    private int targetImplantNumber;
    private double incomeMultiplier;
    private double savedAmount;
    private Simulation simulation;

    public Citizen(Simulation simulation,int agentID, int targetImplantNumber, double incomeMultiplier) {
        super(agentID);
        this.incomeMultiplier = incomeMultiplier;
        this.targetImplantNumber = targetImplantNumber;
        this.simulation = simulation;
        this.savedAmount = 0;
    }

    public void doIncomeUpdate() {
        this.savedAmount += simulation.salary.getNextValue() * this.incomeMultiplier;
    }

    public double getSavedAmount(){
        return this.savedAmount;
    }

    public double getIncomeMultiplier(){
        return this.incomeMultiplier;
    }
}
