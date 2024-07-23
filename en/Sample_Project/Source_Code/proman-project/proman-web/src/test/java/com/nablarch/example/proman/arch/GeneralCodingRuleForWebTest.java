package com.nablarch.example.proman.arch;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.NoDataException;
import nablarch.common.web.session.SessionKeyNotFoundException;

import jakarta.persistence.OptimisticLockException;

/**
 * Testing on how to implement the web.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman.web", importOptions = DoNotIncludeTests.class)
class GeneralCodingRuleForWebTest {

    @ArchTest
    static final ArchRule doNotUseSessionKeyNotFoundExceptionOutsideOfTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(SessionKeyNotFoundException.class);

    @ArchTest
    static final ArchRule thereAreNoClassesThatUseOptimisticLockExceptionInPackagesOtherThanTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(OptimisticLockException.class);

    @ArchTest
     static  final ArchRule noClassesUsingNoDataExceptionInPackagesOtherThanTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
            .should().dependOnClassesThat().areAssignableTo(NoDataException.class);

}
