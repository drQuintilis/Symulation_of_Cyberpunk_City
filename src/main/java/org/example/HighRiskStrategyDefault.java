package org.example;

public class HighRiskStrategyDefault implements RiskStrategy{
    public static boolean shouldIBuyImplant(Citizen citizen, Implant implant) {
        if(citizen.getDesireBuyImplantNow() * 3 > implant.probOfFail) return true;
        else return false;
    }
}
