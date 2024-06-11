package org.example.agents;
import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.implants.Implant;
import org.example.riskStrategies.RiskStrategy;

import java.io.IOException;

public class Citizen extends Agent {
    private int targetImplantNumber; // docelowa liczba implantów, którą chce mieć citizen
    private Implant[] implants; // lista jego implantów
    private double incomeMultiplier; // współczynnik odpowiadający za poziom bogactwa citizena (pensja co tick jest pomnożona przez ten współczynnik)
    private double savedAmount; // portfel citizena
    private int desireBuyImplantNow; // poziom chęci do kupienia implantu, ma wpływ na to żeby citizeny kupowali gorsze implanty
    private RiskStrategy riskStrategy; // odpowiada za to, o ile citizen jest ryzykowny przy wyborze implantów (wzorzec programowania - strategia)

    public Citizen( // konstruktor citizena
            Simulation simulation,
            CitySquare citySquare,
            int agentID,
            int targetImplantNumber,
            RiskStrategy riskStrategy) {
        super(agentID, simulation, citySquare);
        this.incomeMultiplier = simulation.getInequality().getNextValue();
        this.targetImplantNumber = targetImplantNumber;
        this.savedAmount = 0;
        this.implants = new Implant[this.targetImplantNumber];
        this.desireBuyImplantNow = this.targetImplantNumber;
        this.riskStrategy = riskStrategy;
        System.out.println("Citizen ID: " + this.agentID + " created with target implant number: " + this.targetImplantNumber);
    }

    public void buyImplant(){ // proces kupienia i rejestracji implantu
        if (this.savedAmount == 0) return;
        Implant implant = currentSimulation.getMarket().buyImplant(this.savedAmount);
//        System.out.println("Citizen ID: " + this.agentID + " attempts to buy implant with saved amount: " + this.savedAmount);
        if (this.riskStrategy.shouldIBuyImplant(this, implant)) { // w zależności od wybranej strategii citizena on decyduje czy kupować implant
            try {
                implant.connectImplant(this);
//                System.out.println("Citizen ID: " + this.agentID + " successfully bought and connected implant: " + implant);
            } catch (IOException e) {
                System.out.println("Citizen ID: " + this.agentID + " failed to connect implant due to IOException.");
            }
            for (int i = 0; i < implants.length; i++) {
                if (implants[i] == null) { //sprawdzamy, czy jest jeszcze wolne miejsce dla implantu licząc jego docelową liczbę implantów
                    implants[i] = implant;
                    break;
                }
            }
            this.desireBuyImplantNow = targetImplantNumber - getActualNumberOfImplants(); // poziom chęci do kupienia nowego implantu zależy od ilości wolnych miejsc
            this.savedAmount = 0; // citizen zawsze kupuje implant za całą kwotę, którą ma w portfelu obecnie
        } else {
//            System.out.println("Citizen ID: " + this.agentID + " decided not to buy the implant.");
            if (this.getActualNumberOfImplants() != this.targetImplantNumber) {
                this.desireBuyImplantNow += 1; // za każdym razem, gdy citizen nie kupuje implantu z powodu, że nie pasował mu, to wzrasta jego chęć do kupienia nowych implantów o 1
            }
        }
    }

    public int getActualNumberOfImplants() { // dostajemy liczbę już ustawionych implantów, zajętych komórek
        int actualNumberOfImplants = 0;
        for (Implant implant : implants) {
            if (implant != null) {
                actualNumberOfImplants += 1;
            }
        }
        return actualNumberOfImplants;
    }

    public void checkImplant() { //"rzut monetą" dla citizena, definiuje, czy citizen zostanie cyberpsycho, czy nie
        double sum = 0;
        double middleValue = 0;

        for (Implant implant : implants) {
            if (implant != null) {
                sum += implant.getProbOfFailReal();
            }
        }
        middleValue = sum / getActualNumberOfImplants(); // średnia wartość probOfFailReal w liście implantów u citizena
        double i = (Math.random()*101);
        if (i <= middleValue) { // jeśli random jest mniejszy od średniej wartości szansy zwariować się napisanej na implantach, to zostaje sie cyberpsycho
//            System.out.println("Citizen ID: " + this.agentID + " has a failure probability of " + middleValue + " and will go crazy.");
            goCrazy();
        } else {
//            System.out.println("Citizen ID: " + this.agentID + " has a failure probability of " + middleValue + " and will stay sane.");
        }
    }

    public void doTick(TickSteps step) {
        if (!this.isDead) {
            if (step == TickSteps.IMPLANT_STATUS_UPDATE) checkImplant(); // tick, który sprawdza, czy implanty nie wymknęły się spod kontroli
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement(); // tick z prośbą od citizenów o poruszanie się do innej dzielnicy w grafie
            if (step == TickSteps.ECONOMICS_UPDATE) doEconomics(); // tick, który nalicza pieniądze citizenowi
        }
    }

    protected void doMovement() {
        if (this.position.isPsychoHere()) { // citizen sprawdza, czy nie pojawił się na jego dzielnice psycho
            this.currentSimulation.getMaxtak().callThePolice(this.position.squareID); // jesli tak, to przekazuje tę informację do diału maxtak
            System.out.println("Citizen ID: " + this.agentID + " encountered a psycho and called the police.");
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[ //po czym ucieka na dowolną sąsiednią dzielnicę
                            (int) (Math.random() * neighbourSquares.length)
                            ].squareID
            );
        } else if (Math.random() * 100 <= 20) { // jeśli psycho nie ma, to z szansą 20%, citizen spaceruje do dowolnej sąsiedniej dzielnicy, albo zostaje się na miejscu
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[
                            (int) (Math.random() * neighbourSquares.length)
                            ].squareID
            );
            System.out.println("Citizen ID: " + this.agentID + " decided to move to a new square.");
        }
    }

    private void doEconomics() { //citizen dostaje pensje, pomnożoną przez współczynnik bogactwa
        double payment = this.currentSimulation.getSalary().getNextValue() * this.incomeMultiplier;
        this.savedAmount += payment;
//        System.out.println("Citizen ID: " + this.agentID + " received payment: " + payment + ". Total saved amount: " + this.savedAmount);
        buyImplant();
    }

    public void goCrazy(){
        this.die(); // citizen umiera podczas transformacji do cyberpsycho
        CyberPsycho newCP = new CyberPsycho(this.currentSimulation, this.position, this.agentID, this.getActualNumberOfImplants()); // wywoła konstruktor z utworzeniem psycho w tym samym miejscu zamiast citizena
        System.out.println("Citizen ID: " + this.agentID + " went crazy on square: " + newCP.getSquareId());
    }

    @Override
    public String toString() { // dane wyjściowe dla sprawdzenia informacji
        String arrayOfImplants = "";
        for (Implant implant : this.implants) {
            if (implant != null) {
                arrayOfImplants = arrayOfImplants + implant.toString();
            }
        }
        return "Citizen#: " + this.agentID + "\n" +
                "Saved amount: " + String.format("%.2f", this.savedAmount) + "\n" +
                "Multiplier: " + String.format("%.2f", this.incomeMultiplier) + "\n" +
                "Target implant number: " + this.targetImplantNumber + "\n" +
                "Desire to buy implant: " + this.desireBuyImplantNow + "\n" +
                "Array of implants: " + arrayOfImplants + "\n" +
                "Dead: " + this.isDead + "\n" +
                "Type of risk strategy: " + this.riskStrategy.getClass().getName();
    }

    // gettery
    public boolean getIsDead() {
        return isDead;
    }

    public double getSavedAmount() {
        return this.savedAmount;
    }

    public double getIncomeMultiplier() {
        return this.incomeMultiplier;
    }

    public int getTargetImplantNumber() {
        return targetImplantNumber;
    }

    public int getDesireBuyImplantNow() {
        return desireBuyImplantNow;
    }

    public Implant[] getImplants() {
        return implants;
    }
}
