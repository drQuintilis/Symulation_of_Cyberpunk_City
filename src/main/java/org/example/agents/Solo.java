package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.riskStrategies.RiskStrategy;

public class Solo extends Citizen{
    public Solo(Simulation simulation, CitySquare citySquare, int agentID, int targetImplantNumber, double incomeMultiplier, RiskStrategy riskStrategy) {
        super(simulation,citySquare, agentID, targetImplantNumber, incomeMultiplier, riskStrategy);
    }
}
