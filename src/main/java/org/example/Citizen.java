package org.example;
import java.io.IOException;

public class Citizen extends Agent {
    private int targetImplantNumber;
    private Implant[] implants;
    private double incomeMultiplier;
    private double savedAmount;
    private int desireBuyImplantNow;
    private boolean isDead;
    private RiskStrategy riskStrategy;



    public Citizen(Simulation simulation, int agentID, int targetImplantNumber, double incomeMultiplier, RiskStrategy riskStrategy) {
        super(agentID,simulation);
        this.incomeMultiplier = incomeMultiplier;
        this.targetImplantNumber = targetImplantNumber;
        this.savedAmount = 0;
        this.implants = new Implant[this.targetImplantNumber];
        this.desireBuyImplantNow = 0;
        this.isDead = false;
        this.riskStrategy = riskStrategy;
    }


    public void doIncomeUpdate() {
        this.savedAmount += currentSimulation.salary.getNextValue() * this.incomeMultiplier;
    }

    public void buyImplant(){
        Implant implant = currentSimulation.market.buyImplant(this.savedAmount);
        if (this.riskStrategy.shouldIBuyImplant(this, implant)) {
            try {
                implant.connectImplant(this);
            } catch (IOException e) {
            }
            for (int i = 0; i < implants.length; i++) {
                if (implants[i] == null) {
                    implants[i] = implant;
                    break;
                }
            }
            this.desireBuyImplantNow = 0;
            this.savedAmount = 0;
        }
        else {
            if (this.getActualNumberOfImplants() != this.targetImplantNumber) {
                this.desireBuyImplantNow += 1;
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
            if(implant != null){
                sum += implant.getProbOfFailReal();
            }
        }
        middleValue = sum / getActualNumberOfImplants();
        int i = (int)(Math.random()*101);
        if(i <= middleValue) goCrazy();
    }

    public void doTick(){
        if(!isDead) {
            doMovement();
            doEconomics();
            checkImplant();
        }
    }

    private void doMovement() {

    }
    private void doEconomics() {
        this.savedAmount += this.currentSimulation.salary.getNextValue() * this.incomeMultiplier;
        buyImplant();
    }

    public void goCrazy(){
        //die(this.agentID);
        this.isDead = true;
        this.currentSimulation.deRegisterAgent(this);
        CyberPsycho psycho = new CyberPsycho(this.currentSimulation, this.agentID,  this.getActualNumberOfImplants());//call constructor to create psycho
    }

    @Override
    public String toString() {
        String arrayOfImplants = "";
        for(Implant implant: this.implants){
            if(implant != null){
                arrayOfImplants = arrayOfImplants + implant.toString();
            }
        }
        return "Citizen#: " + this.agentID + "\n" +
                "Saved amount: " + String.format("%.2f", this.savedAmount) + "\n" +
                "Multiplier: " + String.format("%.2f", this.incomeMultiplier) + "\n" +
                "Target implant number: " + this.targetImplantNumber + "\n" +
                "Desire to buy implant: " + this.desireBuyImplantNow + "\n" +
                "Array of implants: " + arrayOfImplants + "\n" +
                "Dead: " + this.isDead + "\n" +
                "Type of risk strategy: " + this.riskStrategy.getClass().getName();
    }

    public boolean getIsDead() {
        return isDead;
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
