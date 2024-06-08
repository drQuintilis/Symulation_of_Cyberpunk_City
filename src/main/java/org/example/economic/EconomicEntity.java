package org.example.economic;

public interface EconomicEntity { // интерфейс класса экономики для классов Salary и Inequality
    public double sigma = 0; // параметр распределения: стандартное отклонение (влияет на толщину колокола)
    public double mu = 0; // параметр распределения: среднее значение (двигает колокол по оси ОХ)
    public double getNextValue();
}
