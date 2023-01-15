package edu.boun.edgecloudsim.utils;

import java.util.Random;

public class Variable {

    public static int rand (double lambda) {
        Random random = new Random();
        double u = random.nextDouble();

        int x = 0;
        double cdf = 0;
        while (u >= cdf) {
            x ++;
            cdf = 1 - Math.exp(-1.0 * lambda * x);
        }
        return x;
    }
}
