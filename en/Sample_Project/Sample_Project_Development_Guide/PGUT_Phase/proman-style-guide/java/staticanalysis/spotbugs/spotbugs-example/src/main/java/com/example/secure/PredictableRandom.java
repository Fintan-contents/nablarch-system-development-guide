package com.example.secure;

import java.util.Random;

/**
 * Example of PREDICTABLE_RANDOM code.
 */
public class PredictableRandom {

    String generateSecretToken() {
        Random r = new Random();
        return Long.toHexString(r.nextLong());
    }
}
