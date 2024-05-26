package org.example;

import java.util.ArrayList;
import java.util.List;

public class GenerateImplantList {
    private List<Integer> implants;
    private  GenerateTargetImplantNumber implantGenereted;
    public GenerateImplantList (int population) {
        this.implants = new ArrayList<>(population);
        this.implantGenereted = new GenerateTargetImplantNumber();
        for (int i=0; i < population; i++ ){
            implants.add(implantGenereted.GenerateData());
        }
    }
    public void addCitizen(){
        implants.add(implantGenereted.GenerateData());
    }
    public void printImplants() {
        for (int i = 0; i < implants.size(); i++) {
            System.out.println("Citizen " + (i + 1) + " has " + implants.get(i) + " implants.");
        }
    }

}
