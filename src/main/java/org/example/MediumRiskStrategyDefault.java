package org.example;

public class MediumRiskStrategyDefault implements RiskStrategy{
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant){
        if(citizen.getDesireBuyImplantNow() * 2 > implant.getProbOfFail()) return true;
        else return false;
    }
}
