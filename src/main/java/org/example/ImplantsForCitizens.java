package org.example;

import java.util.ArrayList;
import java.util.List;

public class ImplantsForCitizens {
    private List<Integer> implants;
    private  GenerateTargetImplantNumber implantGenereted;
    public ImplantsForCitizens (int numberOfCitizens){
        implants = new ArrayList<>(numberOfCitizens);
        implantGenereted = new GenerateTargetImplantNumber();
        for (int i=0; i < numberOfCitizens; i++ ){
            implants.add(implantGenereted.GenerateTargetImplantNumber());
        }
    }
    public void addCitizen(){
        implants.add(implantGenereted.GenerateTargetImplantNumber());
    }
    public void printImplants() {
        for (int i = 0; i < implants.size(); i++) {
            System.out.println("Citizen " + (i + 1) + " has " + implants.get(i) + " implants.");
        }
    }

}
