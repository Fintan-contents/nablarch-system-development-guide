/*
 * Header example
 */
package com.example;

/**
 * WriteTagのコード例です。
 * 
 * authorタグがあり、値が設定されています（OK）。
 * 
 * @author example
 */
public class WriteTagExample {
}

/**
 * authorタグがありません（NG）。
 * 
 */
interface Ng1WriteTagExample {
}

/**
 * authorタグはありますが、値がありません（NG）。
 * 
 * @author
 */
interface Ng2WriteTagExample {
}
