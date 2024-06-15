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

    /**
     * konstruktor klasy
     * @param simulation aktywna symulacja
     * @param citySquare klasa dzielnicy planszy
     * @param agentID numer agenta
     * @param targetImplantNumber celow ilość implantów u citizena
     * @param riskStrategy jaką strategię kupowania implantów ma citizen
     */
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

    /**
     * Metoda buyImplant() przeprowadza proces zakupu i rejestracji implantu.
     *
     * Ta metoda sprawdza, czy agent ma wystarczającą ilość zaoszczędzonych środków
     * na zakup implantu. Jeśli tak, próbuje kupić implant z rynku i decyduje,
     * czy implant powinien zostać kupiony na podstawie strategii ryzyka agenta.
     * Jeśli implant zostanie kupiony, jest on podłączany do agenta i rejestrowany
     * w tablicy implantów.
     *
     * @throws IOException jeśli podłączenie implantu nie powiedzie się.
     */
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

    /**
     * Zwraca aktualną liczbę zainstalowanych implantów.
     *
     * Ta metoda przeszukuje tablicę implantów agenta i liczy, ile z nich jest zainstalowanych.
     *
     * @return liczba zainstalowanych implantów jako wartość całkowita.
     */
    public int getActualNumberOfImplants() { // dostajemy liczbę już ustawionych implantów, zajętych komórek
        int actualNumberOfImplants = 0;
        for (Implant implant : implants) {
            if (implant != null) {
                actualNumberOfImplants += 1;
            }
        }
        return actualNumberOfImplants;
    }

    /**
     * Sprawdza, czy agent stanie się cyberpsychopatą.
     *
     * Ta metoda oblicza średnią wartość prawdopodobieństwa awarii implantów
     * agenta i na jej podstawie losowo decyduje, czy agent stanie się cyberpsychopatą.
     * Jest to symulowane poprzez "rzut monetą".
     *
     * Jeśli średnia wartość prawdopodobieństwa awarii implantów jest większa
     * niż losowa liczba, agent staje się cyberpsychopatą, wywołując metodę `goCrazy()`.
     */
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

    /**
     * Wykonuje pojedynczy krok symulacji dla Citizena na podstawie etapu kroku.
     * @param step Etap kroku symulacji określony przez enum TickSteps.
     */
    public void doTick(TickSteps step) {
        if (!this.isDead) {
            if (step == TickSteps.IMPLANT_STATUS_UPDATE) checkImplant(); // tick, który sprawdza, czy implanty nie wymknęły się spod kontroli
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement(); // tick z prośbą od citizenów o poruszanie się do innej dzielnicy w grafie
            if (step == TickSteps.ECONOMICS_UPDATE) doEconomics(); // tick, który nalicza pieniądze citizenowi
        }
    }

    /**
     * Wykonuje ruch agenta do innej dzielnicy.
     *
     * Ta metoda sprawdza, czy w aktualnej dzielnicy agenta znajduje się cyberpsychopata.
     * Jeśli tak, agent informuje o tym Maxtak i ucieka do losowej sąsiedniej dzielnicy.
     * Jeśli nie ma cyberpsychopaty, agent ma 20% szans na przemieszczenie się do losowej
     * sąsiedniej dzielnicy, w przeciwnym razie pozostaje na miejscu.
     */
    protected void doMovement() {
        if (this.position.isPsychoHere()) { // citizen sprawdza, czy nie pojawił się na jego dzielnice psycho
            this.currentSimulation.getMaxtak().callThePolice(this.position.squareID); // jesli tak, to przekazuje tę informację do diału maxtak
//            System.out.println("Citizen ID: " + this.agentID + " encountered a psycho and called the police.");
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
//            System.out.println("Citizen ID: " + this.agentID + " decided to move to a new square.");
        }
    }

    /**
     * Ta metoda oblicza wypłatę dla agenta na podstawie następnej wartości pensji z symulacji
     * i współczynnika dochodów agenta. Następnie dodaje tę kwotę do zaoszczędzonych środków agenta.
     * Po przyznaniu wypłaty metoda automatycznie inicjuje proces zakupu implantu.
     */
    private void doEconomics() { //citizen dostaje pensje, pomnożoną przez współczynnik bogactwa
        double payment = this.currentSimulation.getSalary().getNextValue() * this.incomeMultiplier;
        this.savedAmount += payment;
//        System.out.println("Citizen ID: " + this.agentID + " received payment: " + payment + ". Total saved amount: " + this.savedAmount);
        buyImplant();
    }

    /**
     *Przekształca agenta w cyberpsycho
     */
    public void goCrazy(){
        this.die(); // citizen umiera podczas transformacji do cyberpsycho
        CyberPsycho newCP = new CyberPsycho(this.currentSimulation, this.position, this.agentID, this.getActualNumberOfImplants()); // wywoła konstruktor z utworzeniem psycho w tym samym miejscu zamiast citizena
        System.out.println("Citizen ID: " + this.agentID + " went crazy on square: " + newCP.getSquareId());
    }

    /**
     * Zwraca tekstową reprezentację stanu agenta.
     * @return String zawierający szczegółowe informacje o stanie agenta.
     */
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

    /**
     * Zwraca wartość wskazującą, czy agent jest martwy.
     *
     * @return true, jeśli agent jest martwy; false, jeśli żyje.
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Zwraca ilość zaoszczędzonych środków przez agenta.
     *
     * @return wartość typu double reprezentująca zaoszczędzoną kwotę.
     */
    public double getSavedAmount() {
        return this.savedAmount;
    }

    /**
     * Zwraca mnożnik dochodów agenta.
     *
     * @return wartość typu double reprezentująca mnożnik dochodów.
     */
    public double getIncomeMultiplier() {
        return this.incomeMultiplier;
    }

    /**
     * Zwraca ilość docelowych implantów.
     *
     * @return wartość typu int reprezentująca ilosc docelowych implantów.
     */
    public int getTargetImplantNumber() {
        return targetImplantNumber;
    }

    /**
     * Zwraca poziom pożądania zakupu implantu przez agenta.
     *
     * @return wartość typu int reprezentująca poziom pożądania zakupu implantu.
     */
    public int getDesireBuyImplantNow() {
        return desireBuyImplantNow;
    }

    /**
     * Zwraca tablicę implantów posiadanych przez agenta.
     *
     * @return tablica obiektów typu Implant reprezentująca implanty agenta.
     */
    public Implant[] getImplants() {
        return implants;
    }
}
