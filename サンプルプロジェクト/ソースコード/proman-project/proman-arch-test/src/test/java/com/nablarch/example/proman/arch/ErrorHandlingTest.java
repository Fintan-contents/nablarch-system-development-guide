package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.NoDataException;
import org.junit.runner.RunWith;

import javax.persistence.OptimisticLockException;

/**
 * ハンドリングの仕方までは見れないため、使用されてるか（依存しているか）どうかまでを確認する。
 * throwsで宣言されているものについては確認できる。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class ErrorHandlingTest {

    /**
     * common以外のパッケージで {@link OptimisticLockException} に依存させない。
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
