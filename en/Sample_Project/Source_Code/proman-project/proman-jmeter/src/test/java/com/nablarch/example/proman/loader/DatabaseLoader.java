package com.nablarch.example.proman.loader;

import com.nablarch.example.proman.Configurations;
import jp.co.tis.adc.CommandManager;
import jp.co.tis.adc.InsertManager;
import jp.co.tis.adc.csv.CsvInsertManager;
import jp.co.tis.adc.db.DbSetting;

import java.io.File;
import java.io.IOException;

/**
 * Class for loading initial data into the database.
 * <p/>
 * The YoyoTool is used for loading.
 *
 * @author Tanaka Tomoyuki
 */
public class DatabaseLoader {

    private final Configurations config;

    /**
     * Constructor.
     * @param config Configuration Objects
     */
    public DatabaseLoader(Configurations config) {
        this.config = config;
    }

    /**
     * Load with the data defined in the Excel file.
     * @param file YoyoTool definition files
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
