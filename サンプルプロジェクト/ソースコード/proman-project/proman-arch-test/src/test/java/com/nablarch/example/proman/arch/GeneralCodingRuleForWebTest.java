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

/**
 * webの実装方法に関するテスト。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class GeneralCodingRuleForWebTest {

    @ArchTest
    public static final ArchRule 基盤以外でSessionKeyNotFoundExceptionを使用しないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(SessionKeyNotFoundException.class);

    @ArchTest
    public static final ArchRule 基盤以外のパッケージでOptimisticLockExceptionを使用しているクラスがないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(OptimisticLockException.class);

    @ArchTest
    public  static  final ArchRule 基盤以外のパッケージでNoDataExceptionを使用しているクラスがないこと =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
            .should().dependOnClassesThat().areAssignableTo(NoDataException.class);

}
