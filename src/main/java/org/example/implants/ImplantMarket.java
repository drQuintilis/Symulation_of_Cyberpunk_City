package org.example.implants;

import org.example.Main;
import org.example.agents.Citizen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImplantMarket {
    public float implantCostLevel; // parametr, który wskazuje na ogólne koszty implantów na rynku
    public float implantFailureDispersion; // parametr, który robi rozbieg wartości pomiędzy rzeczywistą szansą zwariować się a "napisaną"
    public int maxProbOfFail; // ograniczamy maksymalną wartość szansy po to, żeby zaniżyć próg częstotliwości pojawiania psycho
    public List<Implant> implantList;
    private Random random;

    public ImplantMarket(float implantCostLevel, float implantFailureDispersion, int maxProbOfFail){ //konstruktor ImplantMarketu
        this.implantList = new LinkedList<>();
        this.random = new Random();
        this.implantCostLevel = implantCostLevel;
        this.implantFailureDispersion = implantFailureDispersion;
        this.maxProbOfFail = maxProbOfFail;
    }

    public Implant buyImplant(double proposedPrice) {
        float countProbOfFail = (float) (this.implantCostLevel / proposedPrice); // ustala szansę zwariować się w zależności od zaproponowanej kwoty pieniędzy
        countProbOfFail = Math.min(countProbOfFail, this.maxProbOfFail); // ograniczamy maksymalną wartość ProbOfFail, jeśli ona wychodzi poza dopuszczone ustalone znaczenie
        float countProbOfFailReal = (float) (countProbOfFail * this.random.nextGaussian(1, this.implantFailureDispersion)); // wyliczamy rzeczywistą szansę zwariować się przez rozkład normalny
        countProbOfFailReal = Math.clamp(countProbOfFailReal, 0.0001f, 20); // ograniczamy maksymalną wartość ProbOfFailReal
        Implant implant = new Implant(countProbOfFail, countProbOfFailReal); // tworzymy nowy implant, który pasuje pod dane ustalone wyżej
        return implant;
    }
}
