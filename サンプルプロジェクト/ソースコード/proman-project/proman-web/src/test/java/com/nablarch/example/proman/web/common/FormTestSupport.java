package com.nablarch.example.proman.web.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Form用のテストサポート<br>
 * priveteメソッド実行補助
 *
 * @param <T> 対象クラス
 */
public class FormTestSupport<T> {
    /**
     * メソッド実行
     * 
     * @param testClass 対象クラス
     * @param target 対象メソッド
     * @param testObject 実行対象オブジェクト
     * @return 実行結果
     */
    public Object doMethod(Class<T> testClass, String target, Object testObject) {
        try {
            Method testMethod = testClass.getDeclaredMethod(target);
            testMethod.setAccessible(true);
            return testMethod.invoke(testObject, new Object[0]);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
