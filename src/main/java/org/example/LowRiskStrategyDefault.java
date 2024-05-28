package org.example;

public class LowRiskStrategyDefault implements RiskStrategy {
    public static boolean shouldIBuyImplant(Citizen citizen, Implant implant){
        if(citizen.getDesireBuyImplantNow() > implant.probOfFail) return true;
        else return false;
    }
}
