package org.example.riskStrategies;

import org.example.agents.Citizen;
import org.example.implants.Implant;

public class HighRiskStrategyDefault implements RiskStrategy{
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant) {
        if(citizen.getDesireBuyImplantNow() * 3 > implant.getProbOfFail()) return true;
        else return false;
    }
}
