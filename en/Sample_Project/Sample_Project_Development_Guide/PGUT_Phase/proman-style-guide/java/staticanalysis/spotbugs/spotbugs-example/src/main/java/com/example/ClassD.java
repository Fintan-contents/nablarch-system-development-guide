package com.example;

import java.util.Random;

public class ClassD {
    String generateSecretToken() {
        Random r = new Random();
        return Long.toHexString(r.nextLong());
    }
}
