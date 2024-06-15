package org.example.agents;
import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CyberPsycho extends Agent{
    private int strength;
    private List<Agent> agentsOnThisSquare;
    private boolean shouldIMove;

    /**
     * konstruktor klasy Cyberpsycho
     * @param simulation aktywna symulacja
     * @param citySquare klasa dzielnicy planszy
     * @param agentID numer agenta
     * @param strength siła agenta
     */
    public CyberPsycho(Simulation simulation, CitySquare citySquare,  int agentID, int strength) { //konstruktor klasy cyberpsycho
        super(agentID, simulation, citySquare);
        this.strength = strength;
    }
    private int damage;

    /**
     * Ta metoda określa, ile osób w dzielnicy psycho może zaatakować.
     * Jeśli nie znaleziono żadnych agentów, psycho powinien się przemieścić.
     * W przeciwnym razie losowo wybiera i zabija agentów do liczby określonej przez siłę psycho.
     * Jeśli dostępnych jest mniej agentów niż siła psycho, psycho przemieszcza się do innej dzielnicy.
     */
    public void attackEveryone(){ // określa, ile osób w dzielnice psycho może zaatakować
        Agent[] agentsOnThisSquare = this.position.getAttackableAgents();
        if (agentsOnThisSquare.length == 0){
            shouldIMove = true;
            return;
        }
        for (int i = 0; i < strength; i++) {
            agentsOnThisSquare[(int)(Math.random() * agentsOnThisSquare.length)].die(); // wybiera przypadkowo kilka osób (zależy od siły psycho), których zabije
        }
        if (agentsOnThisSquare.length < strength) {
            shouldIMove = true; // jeśli w dzielnice zostaje się za mało osób, których można zabić, to psycho idzie do innej dzielnicy
        }
    }

    /**
     * Wykonuje ruch, jeśli psycho powinien się przemieścić.
     * Jeśli shouldIMove jest prawdziwe, metoda pobiera sąsiednie dzielnice
     * i losowo wybiera jedną z nich, do której psycho się przemieszcza.
     */
    protected void doMovement() {
        if (shouldIMove) {
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[ // idzie do dowolnej sąsiedniej dzielnicy
                            (int)(Math.random()*neighbourSquares.length)
                            ].squareID
            );
        }
    }

    /**
     * Zwraca opis CyberPsycho w postaci Stringa.
     * @return String zawierający numer CyberPsycho oraz informację, o siłe CyberPsycho
     */
    public String toString(){
        return "CyberPsycho ID: " + this.agentID + "\n" +
                "Strenght :" + this.strength;
    }

    /**
     * Atakuje psycho, zwiększając jego obrażenia na podstawie siły ataku.
     * <p>
     * Jeśli psycho jest martwy, metoda nie wykonuje żadnych działań.
     * Jeśli siła ataku wynosi -1, obrażenia zwiększają się o 1.
     * W przeciwnym razie obrażenia zwiększają się o iloraz siły ataku i siły psycho.
     * Jeśli obrażenia są równe lub większe od siły psycho, psycho umiera.
     *
     * @param attStrength siła ataku zadana psycho
     */
    public void attack(int attStrength) {
        if (this.isDead) return; // jeśli psycho umiera to nic się nie dzieje
        if (attStrength == -1 ) damage++; // maxtak zawsze robi damage o 1
        else damage+= (int) (attStrength/strength); // solo robi damage o sile = ilości jego aktualnych implantów, podzieloną przez siłę psycho
        if (damage >= strength) this.die(); // jeśli damage jest większy od siły psycho, co w tym przypadku także jest HP, to psycho umiera
    }

    /**
     * Wykonuje pojedynczy krok symulacji dla CyberPsycho na podstawie etapu kroku.
     * @param step Etap kroku symulacji określony przez enum TickSteps.
     */
    public void doTick(TickSteps step){
        if(!this.isDead) {
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement(); // prosi o poruszanie sie do innej dzielnicy
            if (step == TickSteps.CYBER_PSYCHO_ATTACK) attackEveryone(); // tick odpowiadający za atakowanie wszystkich
        }
    }
}
