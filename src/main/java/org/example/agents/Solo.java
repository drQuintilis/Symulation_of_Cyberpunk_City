package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.riskStrategies.RiskStrategy;

public class Solo extends Citizen{
    /**
     * @param simulation aktywna symulacja
     * @param citySquare klasa dzielnicy planszy
     * @param agentID numer agenta
     * @param targetImplantNumber celow ilość implantów u citizena
     * @param riskStrategy jaką strategię kupowania implantów ma Solo
     */
    public Solo(Simulation simulation, CitySquare citySquare, int agentID, int targetImplantNumber, RiskStrategy riskStrategy) {
        super(simulation,citySquare, agentID, targetImplantNumber, riskStrategy); // konstruktor klasy solo
    }

    /**
     * Sprawdza, czy na bieżącej pozycji jest psycho.
     * Jeśli tak, atakuje psycho z siłą równą liczbie aktualnych implantów.
     */
    private void attackPsycho(){
        if (this.position.isPsychoHere()) { //sprawdza, czy jest psycho w tej dzielnice
            this.position.getPsycho().attack(this.getActualNumberOfImplants()); // atakuje psycho z siłą równą liczbie aktualnych implantów
        }
    }

    /**
     * Wykonuje pojedynczy krok symulacji dla Solo na podstawie etapu kroku.
     * @param step Etap kroku symulacji określony przez enum TickSteps.
     */
    public void doTick(TickSteps step){
        super.doTick(step);
        if(!this.isDead) {
            if (step == TickSteps.MAXTAK_ATTACK) attackPsycho(); // tick, który odpowiada za atak na psycho
        }
    }

    /**
     * Wykonuje ruch solo z określoną szansą na spacer do dowolnej sąsiedniej dzielnicy lub pozostanie na miejscu.
     */
    protected void doMovement() {
        if (Math.random()*100 <= 40) { // z szansą 40%, solo spaceruje do dowolnej sąsiedniej dzielnicy, albo zostaje się na miejscu
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[ // uwaga - solo nigdy nie ucieka od psycho
                            (int)(Math.random()*neighbourSquares.length)
                            ].squareID
            );
        }
    }
}
