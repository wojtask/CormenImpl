package pl.kwojtas.cormenimpl;

import java.util.Random;

public class Chapter5 {

    private Chapter5() { }

    private static double probabilityForBiasedRandom = new Random(System.currentTimeMillis()).nextDouble();

    // solution of 5.1-2
    public static int random(int a, int b) {
        while (a < b) {
            int mid = (a + b) / 2;
            if (Util.random() == 0) {
                a = mid + 1;
            } else {
                b = mid;
            }
        }
        return a;
    }

    // solution of 5.1-3
    public static int unbiasedRandom() {
        int x, y;
        do {
            x = biasedRandom();
            y = biasedRandom();
        } while (x == y);
        return x;
    }

    // solution of 5.1-3
    private static int biasedRandom() {
        Random rand = new Random(System.currentTimeMillis());
        return rand.nextDouble() <= probabilityForBiasedRandom ? 1 : 0;
    }

}
