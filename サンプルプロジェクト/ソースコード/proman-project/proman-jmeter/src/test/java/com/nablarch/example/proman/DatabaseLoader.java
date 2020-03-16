package com.nablarch.example.proman;

import jp.co.tis.adc.CommandManager;
import jp.co.tis.adc.csv.CsvInsertManager;

import java.io.File;

/**
 * データベースに初期データをロードするためのクラス。
 * <p/>
 * ロードには YoyoTool を使用する。
 *
 * @author Tanaka Tomoyuki
 */
public class DatabaseLoader {

    /**
     * Excel ファイルに定義されたデータでロードする。
     * @param file YoyoTool の定義ファイル
     */
    public void loadFromExcel(File file) {
        CommandManager.main(new String[] {file.toString(), "insert"});
    }

    /**
     * CSV ファイルでデータをロードする。
     * <p/>
     * CSV ファイルは、引数で指定した定義ファイルと同じディレクトリに存在する、
     * {@code insert} ディレクトリの下に配置していることを前提とする。
     * <p/>
     * この前提は、 YoyoTool の挙動によるものであるため、
     * CSV の検索仕様については YoyoTool のドキュメント等を参照すること。
     *
     * @param file YoyoTool の定義ファイル
     */
    public void loadFromCsv(File file) {
        CsvInsertManager.main(new String[] {file.toString()});
    }
}
