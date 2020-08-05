package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.DaoContext;
import org.junit.runner.RunWith;

/**
 * Rules on how to implement the Service class.
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class ServiceClassRuleTest {

    /**
     * If you have DaoContext in a field, it is private_final but not static.
     */
    @ArchTest
    public static final ArchRule DaoContextをfieldに持つ場合private_finalであるがstaticじゃないこと =
            ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();

    /**
     * The constructor with DaoContext as an argument must be package_private.
     */
    @ArchTest
    public static final ArchRule DaoContextを引数に持つコンストラクタはpackage_privateであること =
            ArchRuleDefinition.constructors().that().haveRawParameterTypes(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePackagePrivate();

}
