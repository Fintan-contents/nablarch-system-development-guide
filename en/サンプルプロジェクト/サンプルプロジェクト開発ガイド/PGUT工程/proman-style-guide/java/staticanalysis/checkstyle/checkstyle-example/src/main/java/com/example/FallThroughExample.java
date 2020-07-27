/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * FallThroughのコード例です。
=======
 * Example of FallThrough code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class FallThroughExample {

    /**
<<<<<<< HEAD
     * 会員のランクを表す列挙型です。
=======
     * An enumeration indicating members' rank.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     * @author example
     * @since 1.0.0
     */
    enum MemberRank {
<<<<<<< HEAD
        /** ゴールド会員 */
        GOLD,
        /** シルバー会員 */
        SILVER,
        /** ブロンズ会員 */
=======
        /** Gold member */
        GOLD,
        /** Silver member */
        SILVER,
        /** Bronze member */
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        BRONZE,
    }

    /**
<<<<<<< HEAD
     * FallThroughのコード例です（NG）。
     *
     * @param memberRank 会員のランク
=======
     * Example of FallThrough code (incorrect).
     *
     * @param memberRank: Member rank
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void badExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
<<<<<<< HEAD
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
=======
            // Gold member process...
            bonus = 1000;
        case SILVER:
            // Gold member process has fallen through (incorrect).
            // This bug is caused by forgetting a break.

            // Process for silver member...
            bonus = 100;
            break;
        default:
            // Process for other members...
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
            bonus = 10;
        }
    }

    /**
<<<<<<< HEAD
     * FallThroughのコード例です（OK）。
     *
     * @param memberRank 会員のランク
=======
     * Example of FallThrough code (OK).
     *
     * @param memberRank: Member rank
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void goodExample(MemberRank memberRank) {

        int bonus;
        switch (memberRank) {
        case GOLD:
<<<<<<< HEAD
            // ゴールド会員の処理...
            bonus = 1000;
            break;
        case SILVER:
            // シルバー会員の場合の処理...
            bonus = 100;
            break;
        default:
            // その他場合の処理...
=======
            // Gold member process...
            bonus = 1000;
            break;
        case SILVER:
            // Process for silver member...
            bonus = 100;
            break;
        default:
            // Process for other members...
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
            bonus = 10;
        }
    }

    /**
<<<<<<< HEAD
     * FallThroughのコード例です（OKですがgoodExampleメソッドのような書き方を推奨します）。
     *
     * @param memberRank 会員のランク
=======
     * Example of FallThrough code (OK but writing the code as shown for the goodExample method is recommended).
     *
     * @param memberRank: Member rank
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void notBadExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
        case SILVER:
<<<<<<< HEAD
            // ゴールド会員またはシルバー会員の場合の処理...
            bonus = 500;
            break;
        default:
            // その他場合の処理...
=======
            // Process for gold or silver member...
            bonus = 500;
            break;
        default:
            // Process for other members...
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
            bonus = 10;
        }
    }
}

