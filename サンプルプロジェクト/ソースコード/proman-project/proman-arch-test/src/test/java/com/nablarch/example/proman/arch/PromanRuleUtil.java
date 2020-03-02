package com.nablarch.example.proman.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * ArchUnitを実行する際に使用するメソッドを提供する
 */
public class PromanRuleUtil {

    /**
     * 指定されたクラスを取得させない。
     * @param clazz 取得させないクラス
     * @return 条件に当てはまらないクラス
     */
    static DescribedPredicate<JavaClass> notType(Class<?> clazz) {
        return DescribedPredicate.not(JavaClass.Predicates.equivalentTo(clazz));
    }

    /**
     * 指定されたクラス名を末尾にもつクラスを取得させる。
     * @param suffix クラス名の末尾
     * @return 条件に当てはまるクラス
     */
    static DescribedPredicate<JavaClass> haveEndClassesName(String suffix) {
        return JavaClass.Predicates.simpleNameEndingWith(suffix);
    }

}
