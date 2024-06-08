package org.example;
import java.io.IOException;

public class Citizen extends Agent {
    private int targetImplantNumber;
    private Implant[] implants;
    private double incomeMultiplier;
    private double savedAmount;
    private Simulation simulation;
    private int desireBuyImplantNow;
    private boolean isDead;



    public Citizen(Simulation simulation, int agentID, int targetImplantNumber, double incomeMultiplier) {
        super(agentID);
        this.incomeMultiplier = incomeMultiplier;
        this.targetImplantNumber = targetImplantNumber;
        this.simulation = simulation;
        this.savedAmount = 0;
        this.implants = new Implant[this.targetImplantNumber];
        this.desireBuyImplantNow = 0;
        this.isDead = false;
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

    public int getActualNumberOfImplants() {
        int acctualNumberOfImplants = 0;
        for (Implant implant : implants) {
            if (implant != null) {
                acctualNumberOfImplants += 1;
            }
        }
        return acctualNumberOfImplants;
    }

    public void checkImplant(){
        double sum = 0;
        double middleValue = 0.0;

        for (Implant implant : implants) {
            sum += implant.getProbOfFailReal();
        }
        middleValue = sum / getActualNumberOfImplants();
        int i = (int)(Math.random()*101);
        if(i <= middleValue) goCrazy();
    }

    public void doTick(){
        if(!isDead) {
            doMovement();
            checkImplant();
        }
    }

    private void doMovement() {

    }

    public void goCrazy(){
        //die(this.agentID);
        this.isDead = true;
        CyberPsycho psycho = new CyberPsycho(this.agentID, this.getActualNumberOfImplants());//call constructor to create psycho
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
