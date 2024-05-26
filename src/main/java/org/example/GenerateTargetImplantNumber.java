package org.example;
import java.util.Random;
public class GenerateTargetImplantNumber {
    private Random random;
    GenerateTargetImplantNumber() {
        random = new Random();
    }
    public int GenerateTargetImplantNumber() {
        return random.nextInt(30) + 1;
    }
}
