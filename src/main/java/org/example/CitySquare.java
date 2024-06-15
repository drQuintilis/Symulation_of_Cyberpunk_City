package org.example;

import org.example.agents.*;

import java.util.*;

public class CitySquare {
    public Integer squareID;
    private List<CitySquare> citySquareLinks;
    public List<Agent> agentsOnThisSquare; // lista aktualnych agentów w dzielnice
    private List<Map.Entry<Agent, CitySquare>> movementRequests; // lista zawiera prośby o poruszanie się (agent i gdzie on chcę pójść)
    private int throughput; // przepustowość dzielnicy


    /**
     * Konstruktor klasy CitySquare.
     * <p>
     * @param squareID     Unikalny identyfikator placu miejskiego.
     * @param citySize     Rozmiar miasta, określający liczbę dzielnic.
     * @param throughput   Przepustowość placu miejskiego.
     */
    public CitySquare(Integer squareID, int citySize, int throughput){ // konstruktor klasy CitySquare
        this.agentsOnThisSquare = new LinkedList<>();
        this.citySquareLinks = new ArrayList<>(citySize);
        for (int i = 0; i < citySize; i++) {
            this.citySquareLinks.add(null); // zapisujemy null do listy, żeby lokalizacja dzielnicy w liście była równa ID dzielnicy
        }
        this.squareID = squareID;
        this.movementRequests = new ArrayList<>();
        this.throughput = throughput;
    }

    /**
     * Rejestracja nowego agenta w dzielnicy.
     * <p>
     * @param agent Agent, który ma zostać zarejestrowany.
     */
    public void registerAgent(Agent agent){ // rejestracja nowego agenta w dzielnice
        if (agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.add(agent);
    }

    /**
     * Usuwanie agenta z dzielnicy.
     * <p>
     * @param agent Agent, który ma zostać usunięty.
     */
    public void deregisterAgent(Agent agent){ // usuwanie agenta z dzielnicy
        if (!agentsOnThisSquare.contains(agent)) return;
        this.agentsOnThisSquare.remove(agent);
    }

    /**
     * Łączenie dzielnic w mieście.
     * <p>
     * @param square Dzielnica, która ma zostać połączona z obecną dzielnicą.
     */
    public void connectSquare(CitySquare square) { // łączymy dzielnicy w mieście
        if (this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.set(square.squareID, square);
        square.connectSquare(this);
    }

    /**
     * Odłączanie dzielnic w mieście.
     * <p>
     * @param square Dzielnica, która ma zostać odłączona od obecnej dzielnicy.
     */
    public void disconnectSquare(CitySquare square) { // odłączamy komórki w mieście (nie jest potrzebna, ale na wszelki wypadek)
        if (!this.citySquareLinks.contains(square)) return;
        this.citySquareLinks.remove(square);
        square.disconnectSquare(this);
    }

    /**
     * Ta metoda przeszukuje wszystkich agentów znajdujących się w danej dzielnicy
     * i sprawdza, czy są instancjami klasy Citizen lub MaxtakAgent.
     * Jeśli tak, dodaje ich do listy potencjalnych agentów do ataku.
     *
     * @return Tablica agentów, którzy mogą zostać zaatakowani.
     */
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

    /**
     * Ta metoda przeszukuje wszystkich agentów znajdujących się w danej dzielnicy
     * (placu miejskim) i sprawdza, czy któryś z nich jest instancją klasy CyberPsycho.
     * <p>
     * @return true, jeśli w dzielnicy znajduje się CyberPsycho; w przeciwnym razie false.
     */
    public boolean isPsychoHere() { // sprawdzamy, czy jest wśród innych agentów tej dzielnicy cyberpsycho
        for (Agent agent: this.agentsOnThisSquare) {
            if (agent instanceof CyberPsycho) return true;
        }
        return false;
    }

    /**
     * Ta metoda dodaje prośbę przeniesienia się agenta do listy próśb o przeniesienie.
     * Prośba zawiera agenta oraz docelową dzielnicę.
     * <p>
     * @param currentAgent Agent, który prosi o przeniesienie.
     * @param targetSquare Identyfikator docelowej dzielnicy, do której agent chce się przenieść.
     */
    public void requestMovement(Agent currentAgent, int targetSquare){ // prośba agenta o przeniesienie się do innej dzielnicy
        this.movementRequests.add(Map.entry(currentAgent, this.citySquareLinks.get(targetSquare)));
    }

    /**
     * Ta metoda przetwarza wszystkie prośby o przeniesienie się agentów, które są
     * zapisane w liście movementRequests. Wybiera losowe prośby ruchu do maksymalnej
     * liczby przepustowości dzielnicy i wykonuje je.
     * <p>
     * Dla każdego ruchu:
     * - Rejestruje agenta w nowym miejscu (dzielnicy).
     * - Wyrejestrowuje agenta z obecnego miejsca.
     * - Potwierdza ruch agenta do nowego miejsca.
     * <p>
     * Na koniec resetuje listę prośb o przeniesienie się.
     */
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

    /**
     * Wykonuje pojedynczy krok symulacji dla CitySquare na podstawie etapu kroku.
     * @param step Etap kroku symulacji określony przez enum TickSteps.
     */
    public void doTick(TickSteps step){
        if (step == TickSteps.MOVEMENTS_EXECUTION) doAgentMoves();
        if (step == TickSteps.INFORMATION_PRINT) printSquareStats();
    }

    /**
     * Ta metoda tworzy tymczasową listę do przechowywania połączeń dzielnic, które nie są null.
     * Następnie konwertuje tę listę na tablicę i zwraca ją.
     * <p>
     * @return Tablica obiektów CitySquare reprezentujących połączenia dzielnic, które nie są null.
     */
    public CitySquare[] getCitySquareLinks() { // tworzymy tymczasową listę do przechowywania nie-nullowych połączeń dzielnic
        LinkedList<CitySquare> arrayOfSquares = new LinkedList<CitySquare>();
        for(CitySquare square: this.citySquareLinks){
            if(square != null){
                arrayOfSquares.add(square);
            }
        }
        return arrayOfSquares.toArray(new CitySquare[0]); // konwersja listy arrayOfSquares na tablicę i zwrócenie jej
    }

    /**
     * Ta metoda tworzy listę identyfikatorów (squareID) połączonych dzielnic, które nie są null.
     * Następnie konstruuje i zwraca łańcuch znaków reprezentujący obiekt CitySquare, zawierający
     * identyfikator placu miejskiego, połączenia dzielnic oraz agentów na tym placu.
     * <p>
     * @return Reprezentacja obiektu CitySquare w postaci tekstu.
     */
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

    /**
     * Ta metoda tworzy mapę (HashMap) przechowującą liczbę agentów
     * każdej klasy (Citizen, Solo, MaxtakAgent, CyberPsycho) w dzielnice.
     * Następnie iteruje przez wszystkich agentów w dzielnice, aktualizując mapę statystyk.
     * Na koniec wypisuje identyfikator dzielnicy oraz liczbę agentów każdej klasy.
     */
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

    /**
     * Ta metoda przeszukuje wszystkich agentów znajdujących się w danej dzielnicy
     * i dodaje instancje klasy CyberPsycho do listy. Następnie
     * zwraca losowego CyberPsycho z tej listy.
     * <p>
     * @return Losowy CyberPsycho z listy agentów w dzielnicy.
     */
    public CyberPsycho getPsycho() {
        List<CyberPsycho> cyberPsychos = new ArrayList<CyberPsycho>();
        for (Agent agent: this.agentsOnThisSquare) {
            // sprawdzamy, czy agent jest psycho i dodajemy go do listy wszystkich psycho w dzielnice
            if (agent instanceof CyberPsycho) cyberPsychos.add((CyberPsycho) agent);
        }
        return cyberPsychos.get((int)(Math.random()*cyberPsychos.size())); // zwracamy losowego CyberPsycho z listy
    }

}
