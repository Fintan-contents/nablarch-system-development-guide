package com.nablarch.example.proman.arch;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

/**
 * Testing application structure.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman", importOptions = DoNotIncludeTests.class)
class LayeredArchitectureForWebTest {

    @ArchTest
    static final ArchRule testingTheApplicationStructure = Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Action").definedBy(JavaClass.Predicates.simpleNameEndingWith("Action"))
            .layer("Service").definedBy(JavaClass.Predicates.simpleNameEndingWith("Service"))
            .layer("Form").definedBy(JavaClass.Predicates.simpleNameEndingWith("Form"))
            .whereLayer("Action").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers( "Action")
            .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");

}
