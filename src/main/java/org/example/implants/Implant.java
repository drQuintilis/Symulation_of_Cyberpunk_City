package org.example.implants;

import org.example.agents.Citizen;

import java.io.IOException;
/**
 * Reprezentuje implant, posiadający parametr szansy na awarię.
 */
public class Implant {
    private float probOfFail; // parametr wiadomy podczas zakupu, który wyznacza szansę zwariować się po wstawieniu implanta
    private float probOfFailReal; // rzeczywisty parametr, który wskazuję na prawdziwą szansę, może być mniejsza lub większa
    private Citizen owner;
    /**
     * Konstruktor klasy Implant.
     *<p>
     * @param probOfFail  szansa na awarię implanta która jest na "opakowaniu"
     * @param probOfFailReal rzeczywista szansa na awarię implanta
     */
    public Implant(float probOfFail, float probOfFailReal){ //konstruktor klasy Implant
        this.probOfFail = probOfFail;
        this.probOfFailReal = probOfFailReal;
    }
    /**
     * Łączy implant z właścicielem.
     * @param owner właściciel implanta
     * @throws IOException jeśli implant jest już połączony z właścicielem
     */
    public void connectImplant(Citizen owner) throws IOException {
        if (this.owner == null) this.owner = owner; // implant się dowiaduje, że ma właściciela
        else throw new IOException("Something happened");
    }

    // gettery

    /**
     *
     * @return rzeczywista szansa na awarię implanta
     */
    public float getProbOfFailReal() {
        return probOfFailReal;
    }

    /**
     *
     * @return szansa na awarię implanta która jest na "opakowaniu"
     */
    public float getProbOfFail() {
        return probOfFail;
    }
    /**
     * Zwraca opis implanta w postaci Stringa.
     * @return String zawierający Prob of fail i Prob of fail real implantu
     */
    @Override
    public String toString() {
        return "\nProb of fail: " + String.format("%.2f", this.probOfFail) + "\t" +
                "Prob of fail real: " + String.format("%.2f", this.probOfFailReal);
    }
}
