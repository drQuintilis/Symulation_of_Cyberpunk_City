package org.example;

public class MediumRiskStrategyDefault implements RiskStrategy{
    public static boolean shouldIBuyImplant(Citizen citizen, Implant implant){
        if(citizen.getDesireBuyImplantNow() * 2 > implant.probOfFail) return true;
        else return false;
    }
}
