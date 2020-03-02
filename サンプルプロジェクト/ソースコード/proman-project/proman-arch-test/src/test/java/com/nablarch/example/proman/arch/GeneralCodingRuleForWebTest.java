package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.NoDataException;
import nablarch.common.web.session.SessionKeyNotFoundException;
import org.junit.runner.RunWith;

import javax.persistence.OptimisticLockException;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class GeneralCodingRuleForWebTest {

    /**
     * common以外のパッケージで {@link SessionKeyNotFoundException} に依存させない。
     */
    @ArchTest
    public static final ArchRule 基盤以外でSessionKeyNotFoundExceptionを使用しないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(SessionKeyNotFoundException.class);

    /**
     * common以外のパッケージで {@link javax.persistence.OptimisticLockException} に依存させない。
     */
    @ArchTest
    public static final ArchRule 基盤以外のパッケージでOptimisticLockExceptionを使用しているクラスがないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(OptimisticLockException.class);

    /**
     * common以外のパッケージで {@link NoDataException} に依存させない。
     */
    @ArchTest
    public  static  final ArchRule 基盤以外のパッケージでNoDataExceptionを使用しているクラスがないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
            .should().dependOnClassesThat().areAssignableTo(NoDataException.class);

}
