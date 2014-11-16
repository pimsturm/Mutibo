package mutiboclient.moviesets.org.util;

import java.util.Random;
import android.util.Log;

/** Generate 10 random integers in the range 0..99. */
public final class RandomInteger {
    private static final String TAG = "Random integer";

    public static final int[] generate(int number, int end){
        Log.d(TAG, "Generating " + number + " random integers in range 0.." + (end - 1) + ".");

        //note a single Random object is reused here
        Random randomGenerator = new Random();
        int randomInts[] = new int[number];
        for (int idx = 0; idx < number; ++idx){
            randomInts[idx] = randomGenerator.nextInt(end);
            Log.d(TAG, "Generated : " + randomInts[idx]);
        }

        Log.d(TAG, "Done.");
        return randomInts;
    }
}