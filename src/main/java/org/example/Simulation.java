package org.example;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    Salary salary;
    Inequality inequality;
    List<Citizen> citizen;
    ImplantMarket market;

    public Simulation(){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(10,2);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.citizen = new ArrayList<Citizen>();
        this.market = new ImplantMarket();
        for(int i = 0; i < 1000; i++){
            this.citizen.add(
                    new Citizen(this, i, targetImplantNumber.GenerateData(), this.inequality.getNextValue(), new MediumRiskStrategyDefault()));
        }
    }

    public Simulation(int citizenAmount){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(1,3);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.citizen = new ArrayList<Citizen>();
        this.market = new ImplantMarket();
        for(int i = 0; i < citizenAmount; i++){
            this.citizen.add(new Citizen(this, i, targetImplantNumber.GenerateData(),
                    this.inequality.getNextValue(), new MediumRiskStrategyDefault()));
        }
    }

    public void doTick() {
        for(int i = 0; i< citizen.size(); i++){
            citizen.get(i).doIncomeUpdate();
        }
    }

    public void printCitizenInfo(){
        for(int i = 0; i< citizen.size(); i++){
            Citizen activeCitizen = this.citizen.get(i);
            System.out.println("Citizen#: " + activeCitizen.agentID);
            System.out.println( "Saved amount: " + String.format("%.2f", activeCitizen.getSavedAmount()));
            System.out.println( "Multiplier: " + String.format("%.2f", activeCitizen.getIncomeMultiplier()));
            System.out.println( "Target implant number: " + activeCitizen.getTargetImplantNumber());
            System.out.println("Desire to buy implant: " + activeCitizen.getDesireBuyImplantNow());
            System.out.println("Tablica implantów: " + String.format("%.2f", activeCitizen.getImplants()));
            System.out.println("Dead: " + activeCitizen.getIsDead());
        }
    }
}
