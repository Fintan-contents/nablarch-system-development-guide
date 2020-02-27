package com.nablarch.example.proman.web.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import nablarch.common.web.session.SessionKeyNotFoundException;
import nablarch.common.web.session.SessionUtil;
import nablarch.fw.ExecutionContext;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class GeneralWebCodingRuleTest {
    /**
     * デフォルトのストア以外のセッション配置を禁止。
     */
    @ArchTest
    public static final ArchRule session_put_only_default_store =
            ArchRuleDefinition.noClasses()
                    .should().callMethod(SessionUtil.class, "put", ExecutionContext.class, String.class, Object.class, String.class);

    /**
     * common以外のパッケージで {@link SessionKeyNotFoundException} に依存させない。
     */
    @ArchTest
    public static final ArchRule app_classes_must_not_depend_SessionKeyNotFoundException =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
                    .should().dependOnClassesThat().areAssignableTo(SessionKeyNotFoundException.class);


}
