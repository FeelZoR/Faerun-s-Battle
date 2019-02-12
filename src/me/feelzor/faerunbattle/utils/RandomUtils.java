package me.feelzor.faerunbattle.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random RNG = new Random();

    /**
     * Generates a number between 0 and max (inclusive)
     */
    public static int generateNumber(int max) {
        return generateNumber(0, max);
    }

    /**
     * Generates a number between min and max (both inclusive)
     */
    public static int generateNumber(int min, int max) {
        return RNG.nextInt(max - min + 1) + min; // +1 because bound is exclusive
    }

    /**
     * Throws multiple dice with a result between min and max (both inclusive) and returns the sum of all of them
     */
    public static int throwDice(int nbDice, int min, int max) {
        int sum = 0;
        for (int i = 0; i < nbDice; i++) {
            sum += generateNumber(min, max);
        }

        return sum;
    }
}
