package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.fw.action.BatchAction;
import org.junit.runner.RunWith;

/**
 * Batchの実装方法に関するルール。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch")
public class GeneralCodingRuleForBatchTest {

    @ArchTest
    public static final ArchRule ActionクラスはBatchActionを継承していること =
            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                    .should().beAssignableTo(BatchAction.class);

    @ArchTest
    public static final ArchRule BatchActionを継承しているクラスはActionという名称であること =
            ArchRuleDefinition.classes().that().areAssignableTo(BatchAction.class)
                    .should().haveSimpleNameEndingWith("Action");

}
