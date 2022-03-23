package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.fw.action.BatchAction;

/**
 * Rules on how to implement Batch.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch")
class GeneralCodingRuleForBatchTest {

    /**
     * The Action class must inherit from BatchAction.
     */
    @ArchTest
    static final ArchRule actionClassMustInheritFromBatchAction =
            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                    .should().beAssignableTo(BatchAction.class);

    /**
     * The class that inherits from BatchAction is called Action.
     */
    @ArchTest
    static final ArchRule classesThatInheritFromBatchActionMustBeNamedAction =
            ArchRuleDefinition.classes().that().areAssignableTo(BatchAction.class)
                    .should().haveSimpleNameEndingWith("Action");

}
