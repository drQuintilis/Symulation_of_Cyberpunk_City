package org.example.riskStrategies;

import org.example.agents.Citizen;
import org.example.implants.Implant;

public interface RiskStrategy {
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant); //interface for risk strategies

}
