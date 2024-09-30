package com.example.secure;

import java.util.Random;

/**
 * PREDICTABLE_RANDOM のコード例です。
 */
public class PredictableRandom {

    String generateSecretToken() {
        Random r = new Random();
        return Long.toHexString(r.nextLong());
    }
}
