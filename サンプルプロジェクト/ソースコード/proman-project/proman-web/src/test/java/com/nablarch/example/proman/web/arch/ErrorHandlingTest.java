package com.nablarch.example.proman.web.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
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
    public static final ArchRule app_classes_must_not_depend_OptimisticLockException =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(OptimisticLockException.class);

    /**
     * common以外のパッケージで {@link NoDataException} に依存させない。
     */
    @ArchTest
    public  static  final ArchRule app_classes_must_not_depend_NoDataException =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
            .should().dependOnClassesThat().areAssignableTo(NoDataException.class);
    /**
     * Serviceクラスは {@link ApplicationException} を使用しない。
     */
    @ArchTest
    public  static  final ArchRule service_must_not_depend_ApplicationException =
            ArchRuleDefinition.noClasses().that().haveNameMatching(".+Service")
                    .should().dependOnClassesThat().areAssignableTo(ApplicationException.class);

    /**
     * （throws が宣言されたメソッドだけで確認できるようす）Serviceクラスは {@link ApplicationException} をスローするメソッドがないことをやりたかったけどこの方法ではできない。
     */
    @ArchTest
    public  static  final ArchRule service_must_not_depend_ApplicationException2 =
            ArchRuleDefinition.noMethods().that().areDeclaredInClassesThat().haveNameMatching(".+Service")
                    .should().declareThrowableOfType(ApplicationException.class);
}
