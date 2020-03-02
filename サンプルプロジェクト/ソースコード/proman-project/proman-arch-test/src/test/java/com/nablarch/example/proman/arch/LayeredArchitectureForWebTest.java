package com.nablarch.example.proman.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.runner.RunWith;

/**
 * アプリケーション構成に関するテスト。
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class LayeredArchitectureForWebTest {

    @ArchTest
    public static final ArchRule アプリケーション構成のテスト = Architectures.layeredArchitecture()
            .layer("Action").definedBy(haveEndClassesName("Action"))
            .layer("Service").definedBy(haveEndClassesName("Service"))
            .layer("Form").definedBy(haveEndClassesName("Form"))
            .whereLayer("Action").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers( "Action")
            .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");

    /**
     * 指定されたクラス名を末尾にもつ {@link DescribedPredicate<JavaClass>} を取得する。
     * @param suffix クラス名の末尾
     * @return 条件に当てはまるクラス
     */
    private static DescribedPredicate<JavaClass> haveEndClassesName(String suffix) {
        return JavaClass.Predicates.simpleNameEndingWith(suffix);
    }
}
