package com.nablarch.example.proman.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Provides methods to be used when running ArchUnit.
 */
public class PromanRuleUtil {

    /**
     * Do not let them get the specified class.
     * @param clazz Classes not to be gotten.
     * @return Classes that do not meet the requirements.
     */
    static DescribedPredicate<JavaClass> notType(Class<?>... clazz) {
        return DescribedPredicate.not(JavaClass.Predicates.belongToAnyOf(clazz));
    }

}
