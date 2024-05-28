package org.example;

public class DefaultStrategy implements RiskStrategy{

    public static boolean shouldIBuyImplant(Citizen citizen, Implant implant) {
        return false;
    }
}
