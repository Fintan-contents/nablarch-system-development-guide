/*
 * Header example
 */
package com.example;

/**
 * Example of Indentation code.
 *
 * @author example
 * @since 1.0.0
 */
public class Indentation {

    /**
     * Example of incorrect indentation.
     * @param number: Number
     * @return Converted number
     * @throws IllegalArgumentException When the argument is 0
     */
    public int invalidExample(int number)
    throws IllegalArgumentException {   // throws indent does not meet the criteria.

    int ret;    // Indent for statement in method does not meet the criteria.

        switch (number) {
            // switch and case below do not have the same indent (incorrect)
            case 0:
                throw new IllegalArgumentException("argument 'number' must not be zero.");

            default:
                ret = number + 1;
                break;
        }
        return ret;
    }

    /**
     * Example of correct indentation.
     *
     * @param number: Number
     * @return Converted number
     * @throws IllegalArgumentException When the argument is 0
     */
    public int validExample(int number)
        throws IllegalArgumentException {   // throws indent does not meet the criteria.

        int ret;    // Indent for statement in method meets the criteria.

        switch (number) {
        // The example below is OK as switch and case have the same indent
        case 0:
            throw new IllegalArgumentException("argument 'number' must not be zero.");

        default:
            ret = number + 1;
            break;
        }
        return ret;
    }
}
