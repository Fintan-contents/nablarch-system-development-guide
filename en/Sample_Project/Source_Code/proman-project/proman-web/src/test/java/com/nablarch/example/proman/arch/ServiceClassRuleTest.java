package com.nablarch.example.proman.arch;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.DaoContext;


/**
 * Rules on how to implement the Service class.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman", importOptions = DoNotIncludeTests.class)
class ServiceClassRuleTest {

    @ArchTest
    static final ArchRule ifYouHaveDaoContextAsFieldItMustBePrivateFinalButNotStatic =
            ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();

    /**
     * サービスクラスのDaoContextを引数に持つコンストラクタがpackage privateであること。
     */
    @ArchTest
    static final ArchRule constructorWithDaoContextAsArgumentMustBePackagePrivate =
            ArchRuleDefinition.constructors().that().haveRawParameterTypes(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePackagePrivate();

}
