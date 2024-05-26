package org.example;
import java.util.Arrays;

//public class Citizen{
//    private int targetImplantNumber;
//
//    public Citizen(GenerateTargetImplantNumber implantNumber) {
//        this.targetImplantNumber = implantNumber.GenerateData();
//    }
//    public int getTargetImplantNumber() {
//        return targetImplantNumber;
//    }
//}

public class Citizen {
    private final int[] arrayNumberOfImplants;
    private final int size;

    // Konstruktor do inicjalizacji tablicy implantow u citizena
    public Citizen(GenerateTargetImplantNumber implantNumber) {
        this.size = implantNumber.GenerateData();
        this.arrayNumberOfImplants = new int[this.size];
        fillArrayWithRandomNumbers();
    }
    private void fillArrayWithRandomNumbers(){
        for(int i=0; i < this.size; i++){
            this.arrayNumberOfImplants[i] = (int) (Math.random() * 101);
        }
    }
    public void printArray() {
        System.out.println("Item array (size " + this.size + "): " + Arrays.toString(this.arrayNumberOfImplants));
    }
}