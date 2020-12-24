package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.fw.action.BatchAction;
import org.junit.runner.RunWith;

/**
 * Rules on how to implement Batch.
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch")
public class GeneralCodingRuleForBatchTest {

    /**
     * The Action class must inherit from BatchAction.
     */
    @ArchTest
    public static final ArchRule actionClassMustInheritFromBatchAction =
            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                    .should().beAssignableTo(BatchAction.class);

    /**
     * The class that inherits from BatchAction is called Action.
     */
    @ArchTest
    public static final ArchRule classesThatInheritFromBatchActionMustBeNamedAction =
            ArchRuleDefinition.classes().that().areAssignableTo(BatchAction.class)
                    .should().haveSimpleNameEndingWith("Action");

}
