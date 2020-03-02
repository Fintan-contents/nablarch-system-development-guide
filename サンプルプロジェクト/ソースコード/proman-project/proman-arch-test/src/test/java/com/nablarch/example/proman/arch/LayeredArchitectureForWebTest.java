package com.nablarch.example.proman.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class LayeredArchitectureForWebTest {

    @ArchTest
    public static final ArchRule アプリケーション構成のテスト = Architectures.layeredArchitecture()
            .layer("Action").definedBy(haveEndClasssName("Action"))
            .layer("Service").definedBy(haveEndClasssName("Service"))
            .layer("Form").definedBy(haveEndClasssName("Form"))
            .whereLayer("Action").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers( "Action")
            .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");

    /**
     *
     * @param suffix クラス名の末尾
     * @return
     */
    private static DescribedPredicate<JavaClass> haveEndClasssName(String suffix) {
        return JavaClass.Predicates.simpleNameEndingWith(suffix);
    }
}
