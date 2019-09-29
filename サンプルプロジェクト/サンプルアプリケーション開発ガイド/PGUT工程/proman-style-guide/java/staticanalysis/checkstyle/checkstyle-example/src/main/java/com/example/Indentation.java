/*
 * Header example
 */
package com.example;

/**
 * Indentationのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class Indentation {

    /**
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
            case 0:
                throw new IllegalArgumentException("argument 'number' must not be zero.");

            default:
                ret = number + 1;
                break;
        }
        return ret;
    }

    /**
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
        case 0:
            throw new IllegalArgumentException("argument 'number' must not be zero.");

        default:
            ret = number + 1;
            break;
        }
        return ret;
    }
}
