/*
 * Header example
 */
package com.example;

/**
 * Example of FallThrough code.
 *
 * @author example
 * @since 1.0.0
 */
public class FallThroughExample {

    /**
     * An enumeration indicating members' rank.
     *
     * @author example
     * @since 1.0.0
     */
    enum MemberRank {
        /** Gold member */
        GOLD,
        /** Silver member */
        SILVER,
        /** Bronze member */
        BRONZE,
    }

    /**
     * Example of FallThrough code (incorrect).
     *
     * @param memberRank Member rank
     */
    public void badExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
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
            bonus = 10;
        }
    }

    /**
     * Example of FallThrough code (OK).
     *
     * @param memberRank Member rank
     */
    public void goodExample(MemberRank memberRank) {

        int bonus;
        switch (memberRank) {
        case GOLD:
            // Gold member process...
            bonus = 1000;
            break;
        case SILVER:
            // Process for silver member...
            bonus = 100;
            break;
        default:
            // Process for other members...
            bonus = 10;
        }
    }

    /**
     * Example of FallThrough code (OK but writing the code as shown for the goodExample method is recommended).
     *
     * @param memberRank Member rank
     */
    public void notBadExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
        case SILVER:
            // Process for gold or silver member...
            bonus = 500;
            break;
        default:
            // Process for other members...
            bonus = 10;
        }
    }
}

