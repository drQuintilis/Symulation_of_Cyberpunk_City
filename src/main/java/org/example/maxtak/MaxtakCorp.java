package org.example.maxtak;

import org.example.CitySquare;
import org.example.TickSteps;
import org.example.agents.Agent;
import org.example.agents.MaxtakAgent;
import org.example.Simulation;

import java.util.LinkedList;
import java.util.List;
/**
 * Reprezentuje korporację Maxtak, która zarządza agentami i budżetem.
 */
public class MaxtakCorp {
    public double moneyFlow; // kwota finansowania całej korporacji, naliczana co tick.
    public double costOfActiveUnit; // kwota utrzymania jednego agenta co tick, pensja agentów
    public double costOfHireUnit; // kwota zatrudnienia nowego agenta maxtak
    private double savedAmount; // "portfel" korporacji, jej aktualny budżet
    public List<MaxtakAgent> maxtakAgentList;
    public Simulation simulation;
    private List<Integer> activePsychoSquares; // lista dzielnic, gdzie aktualnie znajduje się psycho

    /**
     * Konstruktor klasy MaxtakCorp.
     *
     * @param simulation aktywna symulacja
     * @param moneyFlow kwota finansowania całej korporacji, naliczana co tick
     * @param costOfActiveUnit kwota utrzymania jednego agenta co tick, pensja agentów
     * @param costOfHireUnit kwota zatrudnienia nowego agenta maxtak
     */
    public MaxtakCorp(Simulation simulation, double moneyFlow, double costOfActiveUnit, double costOfHireUnit){ // konstruktor klasy MaxtakCorp
        this.moneyFlow = moneyFlow;
        this.costOfActiveUnit = costOfActiveUnit;
        this.costOfHireUnit = costOfHireUnit;
        this.savedAmount = 0;
        this.maxtakAgentList = new LinkedList<>();
        this.simulation = simulation;
        this.activePsychoSquares = new LinkedList<>();
    }
    /**
     * Naliczanie pieniędzy do budżetu, liczenie ile zostałop pieniędzy po wypłacie pensij agentom co tick.
     */
    public void doIncomeUpdate() { // naliczanie pieniędzy do budżetu co tick i wyliczanie na pensji agentów
        this.savedAmount += this.moneyFlow;
        this.savedAmount -= this.costOfActiveUnit * this.maxtakAgentList.size();
        System.out.println("Maxtak Corp get " + this.moneyFlow + " of income and paid "+ this.costOfActiveUnit * this.maxtakAgentList.size()+ " of salary. Left " + this.savedAmount);
    }
    /**
     * Aktualizuja ilośći agentów, zatrudniając nowych, jeśli są dostępne środki.
     */
    public void doUpdateAgents(){
        for(int i = 0; ; i++){
            if(this.savedAmount >= this.costOfHireUnit && this.moneyFlow // jeśli zostaną się pieniądze, to korporacja zatrudnia nowych agentów
                    >= (this.costOfActiveUnit * (this.maxtakAgentList.size() + 1))){ // uwaga: korporacja ma mieć pieniądze na kolejny tick, żeby wypłacić wszystkie pensji agentom
                MaxtakAgent newAgent = new MaxtakAgent(this.simulation, this.simulation.getRandomCitySqaureForAgent(), this.simulation.getAgentId()); // tworzenie nowego agenta maxtak
                this.maxtakAgentList.add(newAgent);
                this.savedAmount -= this.costOfHireUnit;
                System.out.println("Created new MaxtakAgent with ID:" + newAgent.agentID + " on square: " + newAgent.getSquareId());
            } else break;
        }
    }
    /**
     * Korporacja otrzymuje informacje o lokalizacji psycho na którejś z dzielnic.
     *<p>
     * @param squareId identyfikator dzielnicy, w której znajduje się psycho
     */
    public void callThePolice(int squareId) { // korporacja otrzymuje informacje o lokalizacji psycho na którejś z dzielnic
        if (!this.activePsychoSquares.contains(squareId)) { // jeśli ta dzielnica nadal nie jest zapisana na listę dzielnic, gdzie znajduje się psycho
            this.activePsychoSquares.add(squareId); // to dodajemy ją do tej listy
            System.out.println("Maxtak Corp received call on: " + squareId);
        }
    }

    /**
     *
     * @return tablica z ID dzielnic, w których znajdują się psycho
     */
    public Integer[] getActivePsychoSquares() { // getter, otrzymujemy listę w postaci tablicy
        return activePsychoSquares.toArray(new Integer[0]); // przekazujemy 0 dlatego, żeby utworzyła się automatycznie nowa tablica (hermetyzacja metoda)
    }

    /**
     * Wykonuje pojedynczy krok symulacji dla MaxtakCorp na podstawie etapu kroku.
     * @param step Etap kroku symulacji określony przez enum TickSteps.
     */
    public void doTick(TickSteps step){
        if (step == TickSteps.ECONOMICS_UPDATE) {
            doIncomeUpdate();
            doUpdateAgents();
        }
    }

    /**
     * Usuwa agenta z listy aktualnych agentów, działą jako jego "śmierć".
     *<p>
     * @param agent agent do usunięcia
     */
    public void deregisterAgent(MaxtakAgent agent){ // usuwamy agenta z listy aktualnych agentów, jego "śmierć"
        this.maxtakAgentList.remove(agent);
    }
    /**
     * Zwraca opis Maxtak w postaci Stringa.
     * @return łańcuch znaków opisujący korporację Maxtak
     */
    @Override
    public String toString() {
        String arrayOfMaxtakAgents = "";
        for(MaxtakAgent maxtakAgent: this.maxtakAgentList){
            if(maxtakAgent != null){
                arrayOfMaxtakAgents = arrayOfMaxtakAgents + maxtakAgent.toString();
            }
        }
        return "MaxtakCorp date:" + "\n" +
                "Money flow: " + this.moneyFlow + "\n" +
                "Cost of active unit: " + this.costOfActiveUnit +"\n" +
                "Cost of hire unit: " + this.costOfHireUnit +"\n" +
                "Saved amount: " + this.savedAmount + "\n" +
                "Array of Maxtak agents: " + arrayOfMaxtakAgents;
    }
    /**
     * Anuluje przekazanie informacji o lokalizacji psycho, gdy psycho już nie znajduje się w tej dzielnicy.
     *<p>
     * @param squareID ID dzielnicy do usunięcia z listy aktywnych dzielnic z psycho
     */
    public void deregisterCall(int squareID) { // anuluje przekazanie informacji o lokalizacji psycho, gdy psycho już nie znajduje się w tej dzielnice
        if (this.activePsychoSquares.contains(squareID)) {
            this.activePsychoSquares.remove((Integer)squareID); // usuwa z listy informację o tej dzielnicy
            System.out.println("Maxtak Corp deregister call on: " + squareID);
        }
    }
}
