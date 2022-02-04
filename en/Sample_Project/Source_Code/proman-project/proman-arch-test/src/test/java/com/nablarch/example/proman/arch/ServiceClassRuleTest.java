package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.DaoContext;

/**
 * Rules on how to implement the Service class.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman")
class ServiceClassRuleTest {

    /**
     * If you have DaoContext in a field, it is private_final but not static.
     */
    @ArchTest
    static final ArchRule ifYouHaveDaoContextAsFieldItMustBePrivateFinalButNotStatic =
            ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();

    /**
     * The constructor with DaoContext as an argument must be package_private.
     */
    @ArchTest
    static final ArchRule constructorWithDaoContextAsArgumentMustBePackagePrivate =
            ArchRuleDefinition.constructors().that().haveRawParameterTypes(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePackagePrivate();

}
