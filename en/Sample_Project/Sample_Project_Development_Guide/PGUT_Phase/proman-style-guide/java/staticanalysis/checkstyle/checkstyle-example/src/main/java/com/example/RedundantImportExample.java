/*
 * Header example
 */
package com.example;

import java.util.List;
import java.util.List; // import is duplicated (incorrect).

import java.lang.StringBuilder; // A class in java.lang is imported (incorrect).

import com.example.VisibilityModifierExample; // A class in the same package is imported (incorrect).

/**
 * Example of RedundantImport code.
 * 
 * @author example
 * @since 1.0.0
 */
public class RedundantImportExample {

    private List<String> foos;
    private StringBuilder bar;
    private VisibilityModifierExample baz;
}

