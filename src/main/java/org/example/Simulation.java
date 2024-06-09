package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;
import org.example.economic.Inequality;
import org.example.economic.Salary;
import org.example.implants.GenerateTargetImplantNumber;
import org.example.implants.ImplantMarket;
import org.example.riskStrategies.HighRiskStrategyDefault;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private Salary salary;
    Inequality inequality;
    List<Agent> agents;
    private ImplantMarket market;

    public Simulation(){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(10,2);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.agents = new ArrayList<Agent>();
        this.market = new ImplantMarket(20000, 0.7f, 20);
        for(int i = 0; i < 1000; i++){
                    new Citizen(this, i, targetImplantNumber.GenerateData(), this.inequality.getNextValue(), new HighRiskStrategyDefault());
        }
    }

    public Simulation(int citizenAmount){
        this.salary = new Salary(100, 40);
        this.inequality = new Inequality(1,3);
        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
        this.agents = new ArrayList<Agent>();
        this.market = new ImplantMarket(20000, 0.7f, 20);
        for(int i = 0; i < citizenAmount; i++){
            new Citizen(this, i, targetImplantNumber.GenerateData(),
                    this.inequality.getNextValue(), new HighRiskStrategyDefault());
        }
    }

    public void doTick() {
        for(int i = 0; i< agents.size(); i++){
            agents.get(i).doTick();
        }
    }

    public void printCitizenInfo(){
        for(int i = 0; i< agents.size(); i++){
            Agent activeCitizen = this.agents.get(i);
            System.out.println(activeCitizen.toString());
        }
    }

    public void registerAgent(Agent agent){
        this.agents.add(agent);
    }

    public void deRegisterAgent(Agent agent){ //"destruction"
        this.agents.remove(agent);
    }


    public ImplantMarket getMarket() {
        return market;
    }

    public Salary getSalary() {
        return salary;
    }
}
