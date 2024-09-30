package com.nablarch.example.proman.arch;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.fw.action.BatchAction;

/**
 * Rules on how to implement Batch.
 */
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch", importOptions = DoNotIncludeTests.class)
class GeneralCodingRuleForBatchTest {

    @ArchTest
    static final ArchRule actionClassMustInheritFromBatchAction =
            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                    .should().beAssignableTo(BatchAction.class);

    @ArchTest
    static final ArchRule classesThatInheritFromBatchActionMustBeNamedAction =
            ArchRuleDefinition.classes().that().areAssignableTo(BatchAction.class)
                    .should().haveSimpleNameEndingWith("Action");

}
