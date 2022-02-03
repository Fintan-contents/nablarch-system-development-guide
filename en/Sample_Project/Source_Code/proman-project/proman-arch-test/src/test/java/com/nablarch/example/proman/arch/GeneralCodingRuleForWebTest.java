package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.NoDataException;
import nablarch.common.web.session.SessionKeyNotFoundException;

import javax.persistence.OptimisticLockException;

/**
 * Testing on how to implement the web.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
class GeneralCodingRuleForWebTest {

    /**
     * Do not use SessionKeyNotFoundException outside of the base.
     */
    @ArchTest
    static final ArchRule doNotUseSessionKeyNotFoundExceptionOutsideOfTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(SessionKeyNotFoundException.class);

    /**
     * No classes using OptimisticLockException in any package other than the base.
     */
    @ArchTest
    static final ArchRule thereAreNoClassesThatUseOptimisticLockExceptionInPackagesOtherThanTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(OptimisticLockException.class);

    /**
     * No classes using NoDataException in non-instrumental packages.
     */
    @ArchTest
     static  final ArchRule noClassesUsingNoDataExceptionInPackagesOtherThanTheInfrastructure =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
            .should().dependOnClassesThat().areAssignableTo(NoDataException.class);

}
