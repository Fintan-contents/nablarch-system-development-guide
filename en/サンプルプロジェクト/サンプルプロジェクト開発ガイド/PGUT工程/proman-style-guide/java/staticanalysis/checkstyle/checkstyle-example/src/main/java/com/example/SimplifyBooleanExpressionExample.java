/*
 * Header example
 */
package com.example;

/**
 * Example of SimplifyBooleanExpression code.
 *
 * @author example
 * @since 1.0.0
 */
public class SimplifyBooleanExpressionExample {

    /**
     * Example of SimplifyBooleanExpression code.
     */
    public void example() {
        boolean b = false;
        if (b == true) {   // Redundant expression (incorrect). Can be replaced with a simpler expression.
        }

        if (b || true) {   // Redundant expression (incorrect). Can be replaced with a simpler expression.
        }

        if (!false) {      // Redundant expression (incorrect). Can be replaced with a simpler expression.
        }
    }
}

