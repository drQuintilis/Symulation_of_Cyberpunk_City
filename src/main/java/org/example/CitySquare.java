package org.example;

import org.example.agents.*;

import java.util.*;

public class CitySquare {
    public Integer squareID;
    private List<CitySquare> citySquareLinks;
    public List<Agent> agentsOnThisSquare; // lista aktualnych agentów w dzielnice
    private List<Map.Entry<Agent, CitySquare>> movementRequests; // lista zawiera prośby o poruszanie się (agent i gdzie on chcę pójść)
    private int throughput; // przepustowość dzielnicy


    public CitySquare(Integer squareID, int citySize){ // konstruktor klasy CitySquare
        this.agentsOnThisSquare = new LinkedList<>();
        this.citySquareLinks = new ArrayList<>(citySize);
        for (int i = 0; i < citySize; i++) {
            this.citySquareLinks.add(null); // zapisujemy null do listy, żeby lokalizacja dzielnicy w liście była równa ID dzielnicy
        }
        this.squareID = squareID;
        this.movementRequests = new ArrayList<>();
        this.throughput = 15;
    }

    public void registerAgent(Agent agent){ // rejestracja nowego agenta w dzielnice
        if (agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.add(agent);
    }

    public void deregisterAgent(Agent agent){ // usuwanie agenta z dzielnicy
        if (!agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.remove(agent);
    }

    public void connectSquare(CitySquare square) { // łączymy dzielnicy w mieście
        if (this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.set(square.squareID, square);
        square.connectSquare(this);
    }

    public void disconnectSquare(CitySquare square) { // odłączamy komórki w mieście (nie jest potrzebna, ale na wszelki wypadek)
        if (!this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.remove(square);
        square.disconnectSquare(this);
    }

    public Agent[] getAttackableAgents() {
        List<Agent> attackableAgents = new ArrayList<Agent>();
        for (Agent agent: this.agentsOnThisSquare) { // sprawdzamy, czy pewny agent jest intencją klasy citizen albo maxtak agenta (czy on jest nimi?)
            if (agent instanceof Citizen) attackableAgents.add(agent);
            if (agent instanceof MaxtakAgent) attackableAgents.add(agent);
        } // jeśli tak, to dodajemy go na listę potencjalnych agentów do ataku
        Agent[] retVal = new Agent[attackableAgents.size()]; // przerobienie listy agentów do tablicy
        attackableAgents.toArray(retVal);
        return retVal;
    }

    public boolean isPsychoHere() { // sprawdzamy, czy jest wśród innych agentów tej dzielnicy cyberpsycho
        for (Agent agent: this.agentsOnThisSquare) {
            if (agent instanceof CyberPsycho) return true;
        }
        return false;
    }

    public void requestMovement(Agent currentAgent, int targetSquare){ // prośba agenta o przeniesienie się do innej dzielnicy
        this.movementRequests.add(Map.entry(currentAgent, this.citySquareLinks.get(targetSquare)));
    }

    public void doAgentMoves(){
        if (this.movementRequests.isEmpty()) return; // jeśli nie ma żadnej prośby o przeniesieniu się, zakończymy metodę
        List<Map.Entry<Agent, CitySquare>> movementsToExecute = new LinkedList<>(); // tworzymy listę ruchów do wykonania
        for (int i = 0; i < this.throughput; i++) { // iteracja do maksymalnej liczby przepustowości dzielnicy
            // wybieramy losową prośbę ruchu z listy movementRequests
            Map.Entry<Agent, CitySquare> currentEntry = movementRequests.get((int)(Math.random() * movementRequests.size()));
            if (movementsToExecute.contains(currentEntry)) continue; // jeśli wybrana prośba ruchu jest już w liście movementsToExecute, pomijamy ją
            movementsToExecute.add(currentEntry); // dodajemy wybraną prośbę ruchu do listy movementsToExecute
        }
        for (Map.Entry<Agent, CitySquare> executingMove: // iteracja przez wszystkie ruchy do wykonania
             movementsToExecute) {
            executingMove.getValue().registerAgent(executingMove.getKey()); // rejestrujemy agenta w nowym miejscu (dzielnice)
            this.deregisterAgent(executingMove.getKey()); // wyrejestrowujemy agenta z obecnego miejsca
            executingMove.getKey().confirmMove(executingMove.getValue()); // potwierdzamy ruch agenta do nowego miejsca
        }
        this.movementRequests = new ArrayList<>(); // resetujemy listę prośy o przeniesieniu się
    }

    public void doTick(TickSteps step){
        if (step == TickSteps.MOVEMENTS_EXECUTION) doAgentMoves();
        if (step == TickSteps.INFORMATION_PRINT) printSquareStats();
    }

    public CitySquare[] getCitySquareLinks() { // tworzymy tymczasową listę do przechowywania nie-nullowych połączeń dzielnic
        LinkedList<CitySquare> arrayOfSquares = new LinkedList<CitySquare>();
        for(CitySquare square: this.citySquareLinks){
            if(square != null){
                arrayOfSquares.add(square);
            }
        }
        return arrayOfSquares.toArray(new CitySquare[0]); // konwersja listy arrayOfSquares na tablicę i zwrócenie jej
    }

    @Override
    public String toString() {
        LinkedList<Integer> arrayOfSquares = new LinkedList<Integer>();
        for(CitySquare square: this.citySquareLinks){
            if(square != null){
                arrayOfSquares.add(square.squareID);
            }
        }
        return "CitySquare{" +
                "\n  squareID=" + squareID +
                ",\n  citySquareLinks=" + arrayOfSquares +
                ",\n  agentsOnThisSquare=" + agentsOnThisSquare +
                "\n}\n";
    }

    public void printSquareStats() {
        HashMap<Class, Integer> squareStats = new HashMap<>();
        squareStats.put(Citizen.class, 0);
        squareStats.put(Solo.class, 0);
        squareStats.put(MaxtakAgent.class, 0);
        squareStats.put(CyberPsycho.class, 0);
        for (Agent agent :
                this.agentsOnThisSquare) {
            if (!squareStats.containsKey(agent.getClass())) squareStats.put(agent.getClass(), 1);
            else squareStats.replace(agent.getClass(), squareStats.get(agent.getClass())+1);
        }
        System.out.println();
        System.out.println("Square ID: " + this.squareID);
        for (Map.Entry<Class, Integer> entry:
             squareStats.entrySet()) {
            System.out.println(entry.getKey().getSimpleName()+": "+entry.getValue());
        }
    }

    public CyberPsycho getPsycho() {
        List<CyberPsycho> cyberPsychos = new ArrayList<CyberPsycho>();
        for (Agent agent: this.agentsOnThisSquare) {
            // sprawdzamy, czy agent jest psycho i dodajemy go do listy wszystkich psycho w dzielnice
            if (agent instanceof CyberPsycho) cyberPsychos.add((CyberPsycho) agent);
        }
        return cyberPsychos.get((int)(Math.random()*cyberPsychos.size())); // zwracamy losowego CyberPsycho z listy
    }

}
