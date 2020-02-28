package com.nablarch.example.proman.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.TOP_LEVEL_CLASSES;
import org.junit.runner.RunWith;
import nablarch.common.dao.DaoContext;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman.web")
public class ServiceRuleTest {

    /**
     * サービスクラスが持つDaoContextのフィールドがprivateかつfinalであることの確認
     * (staticでないことを追加)
     */
    @ArchTest
    public static final ArchRule DaoContextをfieldに持つ場合private_finalであるがstaticじゃないこと =
            ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .and().areDeclaredInClassesThat().haveNameMatching(".+Service")
                .should().beFinal()
                .andShould().bePrivate()
                .andShould().notBeStatic();

    /**
     * サービスクラスのDaoContextを引数に持つコンストラクタがpackage privateであること。
     */
    @ArchTest
    public static final ArchRule DaoContextを引数に持つコンストラクタはpackage_privateであること =
            ArchRuleDefinition.constructors().that().haveRawParameterTypes(DaoContext.class)
                .and().areDeclaredInClassesThat().haveNameMatching(".+Service")
                .should().bePackagePrivate();
}