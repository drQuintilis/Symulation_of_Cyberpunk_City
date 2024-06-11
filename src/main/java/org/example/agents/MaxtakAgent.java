package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.maxtak.MaxtakCorp;

public class MaxtakAgent extends Agent {

    private MaxtakCorp maxtakCorp;
    public MaxtakAgent( // konstruktor klasy maxtak agent
            Simulation simulation,
            CitySquare citySquare,
            int agentID
    ) {
        super(agentID, simulation, citySquare);
        this.maxtakCorp = simulation.getMaxtak();
    }

    public void die(){  // dodatkowa funkcja śmierci, bo agent maxtak potrzebuje deregistracji jeszcze z maxtak corp
        if (this.isDead) return;
        super.die();
        this.maxtakCorp.deregisterAgent(this);
    }

    protected void doMovement() {
        Integer[] activePsychoSquares = this.maxtakCorp.getActivePsychoSquares(); // pobieramy tablicę aktywnych dzielnic z psycho
        if (activePsychoSquares.length == 0) return;
        int[] closestPsycho = null; // zmienna do przechowywania najbliższej ścieżki do psycho
        for (int i = 0; i < activePsychoSquares.length; i++) { // iteracja przez wszystkie aktywne dzielnice z psycho
            int[] path = this.currentSimulation.getCity().getShortestPath(this.position.squareID, activePsychoSquares[i]); // pobieramy najkrótszą ścieżkę z obecnej pozycji do bieżącej dzielnicy z psycho
            if (closestPsycho == null || path.length < closestPsycho.length) {  // sprawdzamy, czy bieżąca ścieżka jest krótsza niż najkrótsza dotychczas znaleziona
                closestPsycho = path; // jeśli tak, aktualizujemy najbliższą ścieżkę do psycho
            }
        }
        if (closestPsycho == null) { // jeśli psycho nie ma, to spaceruje do dowolnej sąsiedniej dzielnicy
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[
                            (int) (Math.random() * neighbourSquares.length)
                            ].squareID
            );
        }
        if (closestPsycho.length == 0) { // jeśli nie znaleziono ścieżki lub najbliższa ścieżka jest pusta
            if (!this.position.isPsychoHere()) this.maxtakCorp.deregisterCall(this.position.squareID); // jeśli na obecnej pozycji nie ma psycho, anulujemy zgłoszenie
        } else this.position.requestMovement(this,closestPsycho[0]); // Prośba o poruszaniu się do pierwszego wierzchołka w najbliższej ścieżce do psycho
    }

    private void attackPsycho(){ // maxtak agenty atakują psycho zawsze z wartością "-1" żeby można było ich odróżnić od ataku solo
        if (this.position.isPsychoHere()) {
            this.position.getPsycho().attack(-1);
        }
    }

    public void doTick(TickSteps step){
        if(!this.isDead) {
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement();
            if (step == TickSteps.MAXTAK_ATTACK) attackPsycho();
        }
    }
}
