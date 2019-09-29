/*
 * Header example
 */
package com.example;

/**
 * FallThroughのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class FallThroughExample {

    /**
     * 会員のランクを表す列挙型です。
     *
     * @author example
     * @since 1.0.0
     */
    enum MemberRank {
        /** ゴールド会員 */
        GOLD,
        /** シルバー会員 */
        SILVER,
        /** ブロンズ会員 */
        BRONZE,
    }

    /**
     * FallThroughのコード例です（NG）。
     *
     * @param memberRank 会員のランク
     */
    public void badExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
            // ゴールド会員の処理...
            bonus = 1000;
        case SILVER:
            // ゴールド会員の処理をフォールスルーをしています（NG）。
            // これはbreak忘れのバグです。

            // シルバー会員の場合の処理...
            bonus = 100;
            break;
        default:
            // その他場合の処理...
            bonus = 10;
        }
    }

    /**
     * FallThroughのコード例です（OK）。
     *
     * @param memberRank 会員のランク
     */
    public void goodExample(MemberRank memberRank) {

        int bonus;
        switch (memberRank) {
        case GOLD:
            // ゴールド会員の処理...
            bonus = 1000;
            break;
        case SILVER:
            // シルバー会員の場合の処理...
            bonus = 100;
            break;
        default:
            // その他場合の処理...
            bonus = 10;
        }
    }

    /**
     * FallThroughのコード例です（OKですがgoodExampleメソッドのような書き方を推奨します）。
     *
     * @param memberRank 会員のランク
     */
    public void notBadExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
        case SILVER:
            // ゴールド会員またはシルバー会員の場合の処理...
            bonus = 500;
            break;
        default:
            // その他場合の処理...
            bonus = 10;
        }
    }
}

