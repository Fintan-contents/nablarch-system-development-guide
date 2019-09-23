/*
 * Header example
 */
package com.example;

import java.util.Objects;

/**
 * EqualsHashCodeのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class EqualsHashCodeExample {

    /**
     * equalsメソッドのみオーバーライドした例（NG）。
     *
     * @author example
     * @since 1.0.0
     */
    public static class EqualsOverride {
        /** ID */
        private final int id;

        /** 名前 */
        private final String name;

        /**
         * コンストラクタ。
         * @param id ID
         * @param name 名前
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

        // equalsメソッドをオーバーライドしているのに、hashCodeメソッドをオーバライドしていません（NG）。
    }


    /**
     * equalsメソッドとhashCodeメソッド両方をオーバーライドした例（OK）
     * @author example
     * @since 1.0.0
     */
    public static class OverrideEqualsAndHashCode {
        /** ID */
        private final int id;

        /** 名前 */
        private final String name;

        /**
         * コンストラクタ。
         * @param id ID
         * @param name 名前
         */
        public OverrideEqualsAndHashCode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // equalsメソッドとhashCodeメソッドを両方ともオーバライドしています（OK）。

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

