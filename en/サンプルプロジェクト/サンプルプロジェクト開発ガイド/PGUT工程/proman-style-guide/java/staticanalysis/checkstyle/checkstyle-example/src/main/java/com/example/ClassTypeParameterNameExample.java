/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * ClassTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
=======
 * Example of ClassTypeParameterName code.
 *
 * Type parameter is one upper case letter (OK).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class ClassTypeParameterNameExample<T> {
}

/**
<<<<<<< HEAD
 * 2文字以上の名前（NG）。
=======
 * Name consists of 2 or more characters (incorrect).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
<<<<<<< HEAD
 * 小文字アルファベットの名前（NG）。
=======
 * Name consists of lower-case letter(s) (incorrect).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
class NgClassTypeParameterNameExample2<t> {
}
