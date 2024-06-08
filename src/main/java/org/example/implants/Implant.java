package org.example.implants;

import org.example.agents.Citizen;

import java.io.IOException;

public class Implant {
    private float probOfFail;
    private float probOfFailReal;
    private Citizen owner;

    public Implant(float probOfFail, float probOfFailReal){
        this.probOfFail = probOfFail;
        this.probOfFailReal = probOfFailReal;
    }

    public void connectImplant(Citizen owner) throws IOException {
        if (this.owner == null) this.owner = owner;
        else throw new IOException("Something happened");
    }

    public float getProbOfFailReal() {
        return probOfFailReal;
    }

    public float getProbOfFail() {
        return probOfFail;
    }

    @Override
    public String toString() {
        return "\nProb of fail: " + String.format("%.2f", this.probOfFail) + "\t" +
                "Prob of fail real: " + String.format("%.2f", this.probOfFailReal);
    }
}
