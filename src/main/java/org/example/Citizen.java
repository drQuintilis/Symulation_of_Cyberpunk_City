package org.example;

public class Citizen {
    private int implants;

    public Citizen(GenerateTargetImplantNumber implantGenereted) {
        this.implants = implantGenereted.GenerateTargetImplantNumber();
    }
    public int getImplants() {
        return implants;
    }
}
