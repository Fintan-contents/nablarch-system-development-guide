package com.nablarch.example.proman.loader;

import com.nablarch.example.proman.Configurations;
import jp.co.tis.adc.CommandManager;
import jp.co.tis.adc.InsertManager;
import jp.co.tis.adc.csv.CsvInsertManager;
import jp.co.tis.adc.db.DbSetting;

import java.io.File;
import java.io.IOException;

/**
 * データベースに初期データをロードするためのクラス。
 * <p/>
 * ロードには YoyoTool を使用する。
 *
 * @author Tanaka Tomoyuki
 */
public class DatabaseLoader {

    private final Configurations config;

    /**
     * コンストラクタ。
     * @param config 設定オブジェクト
     */
    public DatabaseLoader(Configurations config) {
        this.config = config;
    }

    /**
     * Excel ファイルに定義されたデータでロードする。
     * @param file YoyoTool の定義ファイル
     */
    public void loadFromExcel(File file) {
        try {
            DbSetting dbSetting = new DbSetting(
                config.getDatabaseDriver(),
                config.getDatabaseUrl(),
                config.getDatabaseUsername(),
                config.getDatabasePassword()
            );
            InsertManager insertManager = new InsertManager(file.getCanonicalPath(), dbSetting);
            insertManager.insertTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
