package org.example.riskStrategies;

import org.example.agents.Citizen;
import org.example.implants.Implant;

public class LowRiskStrategyDefault implements RiskStrategy {
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant){
        if(citizen.getDesireBuyImplantNow() > implant.getProbOfFail()) return true;
        else return false;
    }
}
