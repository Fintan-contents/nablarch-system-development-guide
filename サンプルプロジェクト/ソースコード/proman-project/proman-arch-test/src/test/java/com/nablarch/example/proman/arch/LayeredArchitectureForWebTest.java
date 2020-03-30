package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.runner.RunWith;

/**
 * アプリケーション構成に関するテスト。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class LayeredArchitectureForWebTest {

    @ArchTest
    public static final ArchRule アプリケーション構成のテスト = Architectures.layeredArchitecture()
            .layer("Action").definedBy(JavaClass.Predicates.simpleNameEndingWith("Action"))
            .layer("Service").definedBy(JavaClass.Predicates.simpleNameEndingWith("Service"))
            .layer("Form").definedBy(JavaClass.Predicates.simpleNameEndingWith("Form"))
            .whereLayer("Action").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers( "Action")
            .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");

}
