package org.example;

public class Citizen{
    private int targetImplantNumber;

    public Citizen(GenerateTargetImplantNumber implantNumber) {
        this.targetImplantNumber = implantNumber.GenerateData();
    }
//    public int getTargetImplantNumber() {
//        return targetImplantNumber;
//    }
}
