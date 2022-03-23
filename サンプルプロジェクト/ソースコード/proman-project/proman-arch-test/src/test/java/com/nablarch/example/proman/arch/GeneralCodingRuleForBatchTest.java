package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.fw.action.BatchAction;

/**
 * Batchの実装方法に関するルール。
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch")
class GeneralCodingRuleForBatchTest {

    @ArchTest
    static final ArchRule ActionクラスはBatchActionを継承していること =
            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                    .should().beAssignableTo(BatchAction.class);

    @ArchTest
    static final ArchRule BatchActionを継承しているクラスはActionという名称であること =
            ArchRuleDefinition.classes().that().areAssignableTo(BatchAction.class)
                    .should().haveSimpleNameEndingWith("Action");

}
