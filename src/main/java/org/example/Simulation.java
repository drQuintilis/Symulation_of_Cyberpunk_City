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
    };
    private final RiskStrategy[] riskStrategies = {  // lista dla rozdzielenia wśród agentów randomowej strategii
            new HighRiskStrategyDefault(),
            new MediumRiskStrategyDefault(),
            new LowRiskStrategyDefault(),
    };

    public Simulation(){ //konstruktor symulacji
        this.random = new Random();
        this.salary = new Salary(
                100,
                40
        );
        this.inequality = new Inequality(
                10,
                2
        );
        this.market = new ImplantMarket(
                20000,
                0.7f,
                20
        );
        this.maxtakCorp = new MaxtakCorp(
                this,
                1000,
                10,
                100
        );
        int[][] linkageList = { //graf = our City
                {3, 5, 7, 9},
                {4, 5, 7, 8},
                {1, 5, 6, 9},
                {2, 5, 6, 8},
                {1, 2, 3, 4, 6, 7},
                {3, 4, 5},
                {1, 2, 5},
                {2, 4},
                {1, 3}
        };
       this.city = new City(linkageList);
       this.agents = new LinkedList<Agent>();
       this.citizenSpawnRate = 5;
       this.soloProcent = 20;
       this.maxPopulation = 200;


        for (int i = 0; i < 200; i++) { // tworzenie citizenów z danymi parametrami
            createNewCitizen();
        }
    }

    private void createNewCitizen() {
        Agent currentCitizen;
        if (random.nextInt(100) > soloProcent) {
            currentCitizen = new Citizen(
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

//    public Simulation(int citizenAmount){
//        this.salary = new Salary(100, 40);
//        this.inequality = new Inequality(1,3);
//        GenerateTargetImplantNumber targetImplantNumber = new GenerateTargetImplantNumber();
//        this.agents = new ArrayList<Agent>();
//        this.market = new ImplantMarket(20000, 0.7f, 20);
//        for(int i = 0; i < citizenAmount; i++){
//            new Citizen(this, city.citySquareList.get(0), i, targetImplantNumber.GenerateData(),
//                    this.inequality.getNextValue(), new HighRiskStrategyDefault());
//        }
//    }

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
        int populationDiff = this.maxPopulation - this.agents.size();
        if (populationDiff > 0) {
            for (int i = 0; i < Math.min(citizenSpawnRate, populationDiff); i++) {
                createNewCitizen();
            }
        }
    }

    public void registerAgent(Agent agent){ // dodaje agenta na listę, jego tworzenie
        this.agents.add(agent);
    }

    public void deRegisterAgent(Agent agent){ // prawie jak "destruction", usuwa agenta z listy, jego "śmierć"
        this.agents.remove(agent);
    }

        //getters
    public ImplantMarket getMarket() {
        return market;
    }

    public Salary getSalary() {
        return salary;
    }

    public Inequality getInequality() {
        return inequality;
    }

    public MaxtakCorp getMaxtak() {
        return maxtakCorp;
    }

    public City getCity() {
        return city;
    }

    public int getAgentId() {
        this.currentAgentId++;
        return this.currentAgentId;
    }

    public CitySquare getRandomCitySqaureForAgent() {  //wybiera randomową dzielnice dla utworzenia agenta
        CitySquare[] citySquares = city.getCitySquareList();
        return citySquares[random.nextInt(citySquares.length)];
    }
}
