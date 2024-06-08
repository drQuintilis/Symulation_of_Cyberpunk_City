package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImplantMarket {
    public int implantCostLevel;
    public float implantFailureDispersion;
    public List<Implant> implantList;
    private Random random;


    public ImplantMarket(){
        this.implantList = new LinkedList<>();
        this.random = new Random();
    }

    public Implant buyImplant(double proposedPrice) {
        return new Implant((float) random.nextInt(101),
                (float) random.nextInt(101));
    }

    public void registerImplant(Implant implant) {
        for(int i = 0; i < implantList.size(); i++) {
            if(implantList.get(i) == implant) return;
        }
        implantList.add(implant);
    }
}
