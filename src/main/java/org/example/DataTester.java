package org.example;

import org.example.economic.EconomicEntity;

public class DataTester {
    public static double[] generateData(EconomicEntity ecEnt, int size){ //передаем с помочью интерфейса в метод
        double[] data = new double[size];                                //все классы которые описываются через него
        for (int i = 0; i < size; i++) {
            data[i] = ecEnt.getNextValue(); //пользуемся методом для получения следующего рандомного значения описанным в интерфейсе
        }
        return data;
    }
}
