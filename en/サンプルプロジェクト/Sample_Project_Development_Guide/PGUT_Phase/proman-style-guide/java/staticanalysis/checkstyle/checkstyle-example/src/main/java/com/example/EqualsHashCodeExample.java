/*
 * Header example
 */
package com.example;

import java.util.Objects;

/**
 * Example of EqualsHashCode code.
 *
 * @author example
 * @since 1.0.0
 */
public class EqualsHashCodeExample {

    /**
     * Example in which only the equals method is overwritten (OK)
     *
     * @author example
     * @since 1.0.0
     */
    public static class EqualsOverride {
        /** ID */
        private final int id;

        /** Name */
        private final String name;

        /**
         * Constructor.
         * @param id ID
         * @param name Name
         */
        public EqualsOverride(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EqualsOverride that = (EqualsOverride) o;
            return id == that.id && Objects.equals(name, that.name);
        }

        // An equals method is overwritten but a hashCode is not (incorrect).
    }


    /**
     * An example where both the equals method and the hashCode method are overwritten (OK)
     * @author example
     * @since 1.0.0
     */
    public static class OverrideEqualsAndHashCode {
        /** ID */
        private final int id;

        /** Name */
        private final String name;

        /**
         * Constructor.
         * @param id ID
         * @param name Name
         */
        public OverrideEqualsAndHashCode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Both the equals method and the hashCode method are overwritten (OK).

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EqualsOverride that = (EqualsOverride) o;
            return id == that.id && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

}

