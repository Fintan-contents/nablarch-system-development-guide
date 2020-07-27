/*
 * Header example
 */
package com.example;

import java.util.Objects;

/**
<<<<<<< HEAD
 * EqualsHashCodeのコード例です。
=======
 * Example of EqualsHashCode code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class EqualsHashCodeExample {

    /**
<<<<<<< HEAD
     * equalsメソッドのみオーバーライドした例（NG）。
=======
     * Example in which only the equals method is overwritten (OK)
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     * @author example
     * @since 1.0.0
     */
    public static class EqualsOverride {
        /** ID */
        private final int id;

<<<<<<< HEAD
        /** 名前 */
        private final String name;

        /**
         * コンストラクタ。
         * @param id ID
         * @param name 名前
=======
        /** Name */
        private final String name;

        /**
         * Constructor.
         * @param id ID
         * @param name: Name
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
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

<<<<<<< HEAD
        // equalsメソッドをオーバーライドしているのに、hashCodeメソッドをオーバライドしていません（NG）。
=======
        // An equals method is overwritten but a hashCode is not (incorrect).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
    }


    /**
<<<<<<< HEAD
     * equalsメソッドとhashCodeメソッド両方をオーバーライドした例（OK）
=======
     * An example where both the equals method and the hashCode method are overwritten (OK)
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     * @author example
     * @since 1.0.0
     */
    public static class OverrideEqualsAndHashCode {
        /** ID */
        private final int id;

<<<<<<< HEAD
        /** 名前 */
        private final String name;

        /**
         * コンストラクタ。
         * @param id ID
         * @param name 名前
=======
        /** Name */
        private final String name;

        /**
         * Constructor.
         * @param id ID
         * @param name: Name
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
         */
        public OverrideEqualsAndHashCode(int id, String name) {
            this.id = id;
            this.name = name;
        }

<<<<<<< HEAD
        // equalsメソッドとhashCodeメソッドを両方ともオーバライドしています（OK）。
=======
        // Both the equals method and the hashCode method are overwritten (OK).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a

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

