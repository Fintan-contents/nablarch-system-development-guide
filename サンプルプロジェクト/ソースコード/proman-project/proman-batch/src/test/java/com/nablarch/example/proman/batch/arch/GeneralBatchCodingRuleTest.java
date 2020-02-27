package com.nablarch.example.proman.batch.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.web.session.SessionKeyNotFoundException;
import nablarch.common.web.session.SessionUtil;
import nablarch.core.db.statement.SqlRow;
import nablarch.fw.ExecutionContext;
import nablarch.fw.action.BatchAction;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.batch")
public class GeneralBatchCodingRuleTest {
    /**
     *
     */
    @ArchTest
    public static final ArchRule batch_extends_BatchAction =
            ArchRuleDefinition.classes().that().haveNameMatching(".+Action")
                    .should().beAssignableTo(BatchAction.class);


}
