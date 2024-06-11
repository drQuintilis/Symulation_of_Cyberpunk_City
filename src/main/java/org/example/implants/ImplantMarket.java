package org.example.implants;

import org.example.Main;
import org.example.agents.Citizen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImplantMarket {
    private float implantCostLevel; // parametr, który wskazuje na ogólne koszty implantów na rynku
    private float implantFailureDispersion; // parametr, który robi rozbieg wartości pomiędzy rzeczywistą szansą zwariować się a "napisaną"
    private float maxProbOfFail; // ograniczamy maksymalną wartość szansy po to, żeby zaniżyć próg częstotliwości pojawiania psycho
    private float minProbOfFail; // ograniczamy min wartość szansy po to, żeby zaniżyć próg częstotliwości pojawiania psycho
    private Random random;

    public ImplantMarket(float implantCostLevel, float implantFailureDispersion, float maxProbOfFail, float minProbOfFail){ //konstruktor ImplantMarketu
        this(
                new Random(),
                implantCostLevel,
                implantFailureDispersion,
                maxProbOfFail,
                minProbOfFail
        );
    }
    public ImplantMarket(Random random, float implantCostLevel, float implantFailureDispersion, float maxProbOfFail, float minProbOfFail){ //konstruktor ImplantMarketu
        this.random = random;
        this.implantCostLevel = implantCostLevel;
        this.implantFailureDispersion = implantFailureDispersion;
        this.maxProbOfFail = maxProbOfFail;
        this.minProbOfFail = minProbOfFail;
    }

    public Implant buyImplant(double proposedPrice) {
        float countProbOfFail = (float) (this.implantCostLevel / proposedPrice); // ustala szansę zwariować się w zależności od zaproponowanej kwoty pieniędzy
        countProbOfFail = Math.min(countProbOfFail, this.maxProbOfFail); // ograniczamy maksymalną wartość ProbOfFail, jeśli ona wychodzi poza dopuszczone ustalone znaczenie
        float countProbOfFailReal = (float) (countProbOfFail * this.random.nextGaussian(1, this.implantFailureDispersion)); // wyliczamy rzeczywistą szansę zwariować się przez rozkład normalny
        countProbOfFailReal = Math.clamp(countProbOfFailReal, minProbOfFail, maxProbOfFail); // ograniczamy maksymalną wartość ProbOfFailReal
        Implant implant = new Implant(countProbOfFail, countProbOfFailReal); // tworzymy nowy implant, który pasuje pod dane ustalone wyżej
        return implant;
    }
}
