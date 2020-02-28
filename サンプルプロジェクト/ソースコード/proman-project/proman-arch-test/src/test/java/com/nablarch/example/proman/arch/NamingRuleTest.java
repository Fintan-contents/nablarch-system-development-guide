package com.nablarch.example.proman.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class NamingRuleTest {

    /**
     * クラスのネーミングルールを確認する。
     * アプリ側で作成するものがService, Action, Form, DTOだけだという仮定にもとづいている。
     * Testも検索対象になってしまうためそこについても変更している。
     */
    @ArchTest
    public static final ArchRule クラスのネーミングルール =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackages("..common..").and().areTopLevelClasses()
                    .should().haveNameNotMatching(".+Service")
                    .andShould().haveNameNotMatching(".+Action")
                    .andShould().haveNameNotMatching(".+Form")
                    .andShould().haveNameNotMatching(".+DTO")
                    .andShould().haveNameNotMatching(".+Test");

}
