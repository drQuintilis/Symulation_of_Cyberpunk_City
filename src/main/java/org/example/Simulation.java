package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.DataTester.generateData; // импорт метода из класса DataTester

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
            this.citizen.add(new Citizen(this, i, targetImplantNumber.GenerateData(),
                    this.inequality.getNextValue()));
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
                    this.inequality.getNextValue()));
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
            System.out.println(
                    "Citizen #" + activeCitizen.agentID +
                            ", money:" + String.format("%.2f", activeCitizen.getSavedAmount()) +
                            ", multiplier: " + String.format("%.2f", activeCitizen.getIncomeMultiplier()) +
                            ", target implants: " + activeCitizen.getTargetImplantNumber()
            );
        }
    }
}
