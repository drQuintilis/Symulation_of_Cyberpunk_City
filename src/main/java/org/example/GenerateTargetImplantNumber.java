package org.example;
import java.util.Random;
public class GenerateTargetImplantNumber {
    private Random random;
    public GenerateTargetImplantNumber() {
        random = new Random();
    }
    public int GenerateData() {
        return random.nextInt(30) + 1;
    }
}
