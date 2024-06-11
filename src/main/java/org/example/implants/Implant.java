package org.example.implants;

import org.example.agents.Citizen;

import java.io.IOException;

public class Implant {
    private float probOfFail; // parametr wiadomy podczas zakupu, który wyznacza szansę zwariować się po wstawieniu implanta
    private float probOfFailReal; // rzeczywisty parametr, który wskazuję na prawdziwą szansę, może być mniejsza lub większa
    private Citizen owner;

    public Implant(float probOfFail, float probOfFailReal){ //konstruktor klasy Implant
        this.probOfFail = probOfFail;
        this.probOfFailReal = probOfFailReal;
    }

    public void connectImplant(Citizen owner) throws IOException {
        if (this.owner == null) this.owner = owner; // implant się dowiaduje, że ma właściciela
        else throw new IOException("Something happened");
    }

    // gettery
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
