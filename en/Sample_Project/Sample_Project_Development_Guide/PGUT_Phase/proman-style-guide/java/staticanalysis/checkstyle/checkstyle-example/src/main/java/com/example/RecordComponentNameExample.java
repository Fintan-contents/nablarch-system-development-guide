/*
 * Header example
 */
package com.example;

/**
 * RecordComponentNameのコード例です。
 *
 * @param BadName First character is an upper case letter (incorrect)
 * @param bad_name An underscore is used (incorrect)
 * @param goodName Name follows the rules (OK)
 */
public record RecordComponentNameExample(String BadName,
                                         String bad_name,
                                         String goodName) { }
