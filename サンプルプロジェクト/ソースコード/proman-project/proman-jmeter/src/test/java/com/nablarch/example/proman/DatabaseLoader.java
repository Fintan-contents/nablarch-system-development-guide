package com.nablarch.example.proman;

import jp.co.tis.adc.CommandManager;
import jp.co.tis.adc.csv.CsvInsertManager;

/**
 * データベースに初期データをロードするためのクラス。
 * <p/>
 * ロードには YoyoTool を使用する。
 */
public class DatabaseLoader {

    /**
     * Excel ファイルに定義されたデータでロードする。
     * @param excelFilePath YoyoTool の定義ファイルのパス
     */
    public void loadFromExcel(String excelFilePath) {
        CommandManager.main(new String[] {excelFilePath, "insert"});
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
     * @param excelFilePath YoyoTool の定義ファイルのパス
     */
    public void loadFromCsv(String excelFilePath) {
        CsvInsertManager.main(new String[] {excelFilePath});
    }
}
