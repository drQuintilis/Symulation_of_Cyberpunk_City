package org.example;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    Salary salary;
    Inequality inequality;
    List<Agent> agent;
    ImplantMarket market;

    public Simulation(){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(10,2);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.agent = new ArrayList<Agent>();
        this.market = new ImplantMarket();
        for(int i = 0; i < 1000; i++){
                    new Citizen(this, i, targetImplantNumber.GenerateData(), this.inequality.getNextValue(), new MediumRiskStrategyDefault());
        }
    }

    public Simulation(int citizenAmount){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(1,3);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.agent = new ArrayList<Agent>();
        this.market = new ImplantMarket();
        for(int i = 0; i < citizenAmount; i++){
            new Citizen(this, i, targetImplantNumber.GenerateData(),
                    this.inequality.getNextValue(), new MediumRiskStrategyDefault());
        }
    }

    public void doTick() {
        for(int i = 0; i< agent.size(); i++){
            agent.get(i).doTick();
        }
    }

    public void printCitizenInfo(){
        for(int i = 0; i< agent.size(); i++){
            Agent activeCitizen = this.agent.get(i);
            System.out.println(activeCitizen.toString());
        }
    }

    public void registerAgent(Agent agent){
        this.agent.add(agent);
    }

    public void deRegisterAgent(Agent agent){ //"destruction"
        this.agent.remove(agent);
    }
}
