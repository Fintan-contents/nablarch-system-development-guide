/*
 * Header example
 */
package com.example;

/**
 * Example of VisibilityModifier code.
 * 
 * @author example
 * @since 1.0.0
 */
public class VisibilityModifierExample {

    /**
     * A public instance field is declared (incorrect).
     */
    public int ng1;

    /**
     * A protected instance field is declared (incorrect).
     */
    protected int ng2;

    /**
     * A package private instance field is declared (incorrect).
     */
    int ng3;

    /**
     * A private instance field is declared (OK).
     */
    private int ok1;

    /**
     * Scope can be public for static final (OK).
     */
    public static final int OK2 = 0;
}

