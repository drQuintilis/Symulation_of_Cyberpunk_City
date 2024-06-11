package org.example.implants;

import org.example.Main;
import org.example.agents.Citizen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImplantMarket {
    public float implantCostLevel;
    public float implantFailureDispersion;
    public int maxProbOfFail;
    public List<Implant> implantList;
    private Random random;

    public ImplantMarket(float implantCostLevel, float implantFailureDispersion, int maxProbOfFail){
        this.implantList = new LinkedList<>();
        this.random = new Random();
        this.implantCostLevel = implantCostLevel;
        this.implantFailureDispersion = implantFailureDispersion;
        this.maxProbOfFail = maxProbOfFail;
    }

    public Implant buyImplant(double proposedPrice) {
        float countProbOfFail = (float) (this.implantCostLevel / proposedPrice); //calculate ProbOfFail that is given for citizen in market to decide buy or not
        countProbOfFail = Math.min(countProbOfFail, this.maxProbOfFail); //Limit the ProbOfFail
        float countProbOfFailReal = (float) (countProbOfFail * this.random.nextGaussian(1, this.implantFailureDispersion));
        countProbOfFailReal = Math.clamp(countProbOfFailReal, 0.0001f, 20); //Limit the ProbOfFail
        Implant implant = new Implant(countProbOfFail, countProbOfFailReal); //creating new implant
        return implant;
    }
}
