/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * Indentationのコード例です。
=======
 * Example of Indentation code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class Indentation {

    /**
<<<<<<< HEAD
     * IndentationのNG例です。
     *
     * @param number 数字
     * @return 変換後の数値
     * @throws IllegalArgumentException 引数が0の場合
     */
    public int invalidExample(int number)
    throws IllegalArgumentException {   // throwsのインデントが基準を満たしていません。

    int ret;    // メソッド内ステートメントのインデントが基準を満たしていません。

        switch (number) {
            // 以下、switchとcaseとが同じインデントではありませんNG
=======
     * Example of incorrect indentation.
     *
     * @param number: Number
     * @return Converted number
     * @throws IllegalArgumentException When the argument is 0
     */
    public int invalidExample(int number)
        throws IllegalArgumentException {   // throws indent does not meet the criteria.

        int ret;    // Indent for statement in method does not meet the criteria.

        switch (number) {
            // switch and case below do not have the same indent (incorrect)
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
            case 0:
                throw new IllegalArgumentException("argument 'number' must not be zero.");

            default:
                ret = number + 1;
                break;
        }
        return ret;
    }

    /**
<<<<<<< HEAD
     * IndentationのOK例です。
     *
     * @param number 数字
     * @return 変換後の数値
     * @throws IllegalArgumentException 引数が0の場合
     */
    public int validExample(int number)
            throws IllegalArgumentException {   // throwsのインデントが基準を満たしていません。

        int ret;    // メソッド内ステートメントのインデントが基準を満たしています

        switch (number) {
        // 以下、switchとcaseとが同じインデントであるためOK
=======
     * Example of correct indentation.
     *
     * @param number: Number
     * @return Converted number
     * @throws IllegalArgumentException When the argument is 0
     */
    public int validExample(int number)
        throws IllegalArgumentException {   // throws indents do not meet the criteria.

        int ret;    // Indent for statement in method meets the criteria.

        switch (number) {
        // The example below is OK as switch and case have the same indent
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        case 0:
            throw new IllegalArgumentException("argument 'number' must not be zero.");

        default:
            ret = number + 1;
            break;
        }
        return ret;
    }
}
