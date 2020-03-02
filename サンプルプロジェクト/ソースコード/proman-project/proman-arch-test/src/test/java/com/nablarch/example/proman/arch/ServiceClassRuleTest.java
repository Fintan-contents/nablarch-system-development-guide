package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.DaoContext;
import org.junit.runner.RunWith;

/**
 * Serviceクラスの実装方法に関するルール。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class ServiceClassRuleTest {

    @ArchTest
    public static final ArchRule DaoContextをfieldに持つ場合private_finalであるがstaticじゃないこと =
            ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();

    /**
     * サービスクラスのDaoContextを引数に持つコンストラクタがpackage privateであること。
     */
    @ArchTest
    public static final ArchRule DaoContextを引数に持つコンストラクタはpackage_privateであること =
            ArchRuleDefinition.constructors().that().haveRawParameterTypes(DaoContext.class)
                .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                .should().bePackagePrivate();
}
