package org.example;

import org.example.agents.Agent;
import org.example.agents.Citizen;
import org.example.agents.Solo;
import org.example.economic.Inequality;
import org.example.economic.Salary;
import org.example.implants.ImplantMarket;
import org.example.maxtak.MaxtakCorp;
import org.example.riskStrategies.HighRiskStrategyDefault;
import org.example.riskStrategies.LowRiskStrategyDefault;
import org.example.riskStrategies.MediumRiskStrategyDefault;
import org.example.riskStrategies.RiskStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private Random random;
    private Salary salary;
    private Inequality inequality;
    private List<Agent> agents;
    private ImplantMarket market;

    private City city;
    private MaxtakCorp maxtakCorp;
    private int currentAgentId;
    private int maxPopulation;
    private int citizenSpawnRate;
    private int soloProcent;

    private final TickSteps[] stepOrder = { //porządek, w którym robimy kroki symulacji
            TickSteps.IMPLANT_STATUS_UPDATE,
            TickSteps.CYBER_PSYCHO_ATTACK,
            TickSteps.MOVEMENTS_REQUESTS,
            TickSteps.MOVEMENTS_EXECUTION,
            TickSteps.MAXTAK_ATTACK,
            TickSteps.ECONOMICS_UPDATE,
            TickSteps.INFORMATION_PRINT,
    };
    private final RiskStrategy[] riskStrategies = {  // lista dla rozdzielenia wśród agentów randomowej strategii
            new HighRiskStrategyDefault(),
            new MediumRiskStrategyDefault(),
            new LowRiskStrategyDefault(),
    };

    /**
     * Konstruktor symulacji podstawowy
     */
    public Simulation(){
        this(
                100,
                400,
                10,
                2,
                20000,
                0.7f,
                20,
                0.001f,
                500,
                100,
                1000,
                new int[][]{ //graf, czyli nasze miasto
                        {3, 5, 7, 9},
                        {4, 5, 7, 8},
                        {1, 5, 6, 9},
                        {2, 5, 6, 8},
                        {1, 2, 3, 4, 6, 7},
                        {3, 4, 5},
                        {1, 2, 5},
                        {2, 4},
                        {1, 3}
                },
                15,
                5,
                10,
                1000
        );
    }

    /**
     * Konstruktor symulacji z podanymi parametrami
     *
     * @param salaryMu                 Średnia pensji Citizena.
     * @param salarySigma              Odchylenie standardowe pensji Citizena.
     * @param inequalityMu             Parametr rozkładu: wartość średnia (przesuwa dzwon wartości wzdłuż osi OX)
     * @param inequalitySigma          Parametr rozkładu: odchylenie standardowe (szerokość dzwonka wartości)
     * @param implantCostLevel         Poziom kosztów implantów.
     * @param implantFailureDispersion Rozrzut awarii implantów.
     * @param maxProbOfImplantFail     Maksymalne prawdopodobieństwo awarii implantu.
     * @param minProbOfImplantFail     Minimalne prawdopodobieństwo awarii implantu.
     * @param maxtakIncome             Dochód MaxtakCorp.
     * @param maxtakSalary             Pensja jednego agenta MaxtakCorp.
     * @param maxtakHire               Koszt zatrudnienia nowego agenta MaxtakCorp.
     * @param cityLinkageList          Lista połączeń dzielnic w mieście.
     * @param squareThroughput         Przepustowość dzielnicy.
     * @param citizenSpawnRate         Współczynnik pojawiania się nowych obywateli.
     * @param soloProcent              Procent agentów typu Solo od całej populacji.
     * @param maxPopulation            Maksymalna populacja miasta.
     */
    public Simulation(
            int salaryMu,
            int salarySigma,
            int inequalityMu,
            int inequalitySigma,
            int implantCostLevel,
            float implantFailureDispersion,
            float maxProbOfImplantFail,
            float minProbOfImplantFail,
            int maxtakIncome,
            int maxtakSalary,
            int maxtakHire,
            int[][] cityLinkageList,
            int squareThroughput,
            int citizenSpawnRate,
            int soloProcent,
            int maxPopulation
    ){ //konstruktor symulacji
        this.random = new Random();
        this.salary = new Salary(
                salaryMu,
                salarySigma
        );
        this.inequality = new Inequality(
                inequalityMu,
                inequalitySigma
        );
        this.market = new ImplantMarket(
                implantCostLevel,
                implantFailureDispersion,
                maxProbOfImplantFail,
                minProbOfImplantFail
        );
        this.maxtakCorp = new MaxtakCorp(
                this,
                maxtakIncome,
                maxtakSalary,
                maxtakHire
        );
        this.city = new City(cityLinkageList, squareThroughput);
        this.agents = new LinkedList<>();
        this.citizenSpawnRate = citizenSpawnRate;
        this.soloProcent = soloProcent;
        this.maxPopulation = maxPopulation;


        for (int i = 0; i < this.maxPopulation; i++) {
            createNewCitizen();
        }
    }

    /**
     * Tworzy nowego obywatela lub agenta typu Solo i dodaje go do symulacji.
     * <p>
     * Ta metoda losowo decyduje, czy nowy agent będzie obywatelem (Citizen) czy agentem typu Solo,
     * na podstawie zadanego procentu agentów typu Solo (soloProcent). <p>
     * Dla każdego nowego agenta losowane są odpowiednie parametry, takie jak lokalizacja w mieście,
     * unikalny identyfikator, poziom ryzyka oraz strategia ryzyka. <p>
     * Po stworzeniu, agent jest dodawany do symulacji i wypisywana jest informacja o jego ID oraz lokalizacji.
     */
    private void createNewCitizen() {
        Agent currentCitizen;
        if (random.nextInt(100) > soloProcent) { // tworzymy pewny procent solo agentów od całej liczby mieszkańców w mieście
            currentCitizen = new Citizen( // tworzenie citizena z danymi parametrami
                    this,
                    city.getRandomCitySqaureForAgent(),
                    this.getAgentId(),
                    random.nextInt(15) + 1,
                    riskStrategies[random.nextInt(3)]
            );
        } else {
            currentCitizen = new Solo( // tworzenie solo z danymi parametrami
                    this,
                    city.getRandomCitySqaureForAgent(),
                    this.getAgentId(),
                    random.nextInt(15) + 1,
                    riskStrategies[random.nextInt(3)]
            );
        }
        System.out.println("Created new "+currentCitizen.getClass().getSimpleName()+" with ID: " + currentCitizen.agentID + " on square: " + currentCitizen.getSquareId());
    }


    /**
     * Wykonuje jeden cykl symulacji.
     * <p>
     * Ta metoda przechodzi przez listę kroków (stepOrder) zdefiniowaną przez enum TickSteps i wykonuje działania
     * dla każdej z tych kroków dla korporacji MaxtakCorp, miasta oraz wszystkich agentów.
     * <p>
     * Po wykonaniu kroków, metoda sprawdza aktualną populację obywateli (Citizen) w symulacji.
     * Jeśli liczba obywateli jest mniejsza od maksymalnej populacji (maxPopulation), tworzy nowych obywateli
     * w ilości zależnej od współczynnika pojawiania się obywateli (citizenSpawnRate) oraz różnicy między
     * maksymalną populacją a aktualną liczbą obywateli.
     */
    public void doTick() {
        for (TickSteps step:  // przechodzimy po liście enum z krokami
             stepOrder) {
            this.maxtakCorp.doTick(step);
            this.city.doTick(step);
            for (Agent agent:
                 this.agents.toArray(new Agent[0])) {
                agent.doTick(step);
            }
        }
        // przez ograniczenie maksymalnej liczby mieszkańców, kiesy są wolne miejsca to tworzymy nowych citizenów
        int currentPopulation = 0;
        for (Agent agent:
                this.agents
        ){
            if (agent instanceof Citizen) currentPopulation++;
        }
        int populationDiff = this.maxPopulation - currentPopulation;
        if (populationDiff > 0) {
            for (int i = 0; i < Math.min(citizenSpawnRate, populationDiff); i++) {
                createNewCitizen();
            }
        }
    }

    /**
     * Ta metoda rejestruje nowego agenta, dodając go do listy agentów w symulacji.
     *
     * @param agent Agent, który ma zostać dodany do listy.
     */
    public void registerAgent(Agent agent){ // dodaje agenta na listę, jego tworzenie
        this.agents.add(agent);
    }

    /**
     * Ta metoda wyrejestrowuje agenta, usuwając go z listy agentów w symulacji.
     *
     * @param agent Agent, który ma zostać usunięty z listy.
     */
    public void deRegisterAgent(Agent agent){ // prawie jak "destruction", usuwa agenta z listy, jego "śmierć"
        this.agents.remove(agent);
    }

    /**
     * @return getter obiektu ImplantMarketu
     */
    //gettery
    public ImplantMarket getMarket() {
        return market;
    }

    /**
     * @return getter parametru Salary
     */
    public Salary getSalary() {
        return salary;
    }

    /**
     * @return getter parametru Inequality
     */
    public Inequality getInequality() {
        return inequality;
    }

    /**
     * @return getter obiektu MaxtakCorp
     */
    public MaxtakCorp getMaxtak() {
        return maxtakCorp;
    }

    /**
     * @return getter obiektu City
     */
    public City getCity() {
        return city;
    }

    /**
     * @return getter identyfikatora agenta
     */
    public int getAgentId() {
        this.currentAgentId++;
        return this.currentAgentId;
    }

    /**
     * Getter, zwracający losową dzielnicę (CitySquare) z listy dzielnic w mieście,
     * która może być użyta do utworzenia nowego agenta.
     *
     * @return Losowa dzielnica (CitySquare) do utworzenia agenta.
     */
    public CitySquare getRandomCitySqaureForAgent() {  //wybiera randomową dzielnice dla utworzenia agenta
        CitySquare[] citySquares = city.getCitySquareList();
        return citySquares[random.nextInt(citySquares.length)];
    }
}
