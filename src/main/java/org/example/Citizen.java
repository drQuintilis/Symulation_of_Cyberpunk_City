package org.example;
import java.io.IOException;
import java.util.Arrays;

public class Citizen extends Agent {
    private int targetImplantNumber;
    private Implant[] implants;
    private double incomeMultiplier;
    private double savedAmount;
    private Simulation simulation;
    private int desireBuyImplantNow;


    public Citizen(Simulation simulation, int agentID, int targetImplantNumber, double incomeMultiplier) {
        super(agentID);
        this.incomeMultiplier = incomeMultiplier;
        this.targetImplantNumber = targetImplantNumber;
        this.simulation = simulation;
        this.savedAmount = 0;
        this.implants = new Implant[this.targetImplantNumber];
        this.desireBuyImplantNow = 0;
    }

    public void doIncomeUpdate() {
        this.savedAmount += simulation.salary.getNextValue() * this.incomeMultiplier;
    }

    public void buyImplant(){
        Implant implant = simulation.market.buyImplant();
        try {
            implant.connectImplant(this);
        } catch (IOException e) {}
        for(int i = 0; i < implants.length; i++) {
            if(implants[i] == null) {
                implants[i] = implant;
                break;
            }
        }
    }

    public double getSavedAmount(){
        return this.savedAmount;
    }

    public double getIncomeMultiplier(){
        return this.incomeMultiplier;
    }

    public int getTargetImplantNumber() {
        return targetImplantNumber;
    }

    public int getDesireBuyImplantNow() {
        return desireBuyImplantNow;
    }

    public Implant[] getImplants() {
        return implants;
    }
}
