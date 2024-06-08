package org.example.agents;

import org.example.Simulation;
import org.example.riskStrategies.RiskStrategy;

public class Solo extends Citizen{
    public Solo(Simulation simulation, int agentID, int targetImplantNumber, double incomeMultiplier, RiskStrategy riskStrategy) {
        super(simulation, agentID, targetImplantNumber, incomeMultiplier, riskStrategy);
    }
}
