package org.example;

import java.io.IOException;

public class Implant {
    public float probOfFail;
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
}
