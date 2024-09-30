package com.nablarch.example.proman.assertion.db;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSetWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * データベースのテーブルの情報をファイル出力するクラス。
 * @author Tanaka Tomoyuki
 */
public class DatabaseExporter {

    /**
     * 指定したExcelファイルに全テーブルの情報を出力する。
     * @param conn 出力対象のデータベース接続
     * @param outputFile 出力先のExcelファイル
     */
    public void export(DatabaseConnection conn, File outputFile) {
        try (OutputStream out = new FileOutputStream(outputFile)) {
            IDataSet dataSet = conn.createDataSet();
            XlsDataSetWriter writer = new XlsDataSetWriter() {
                @Override
                protected Workbook createWorkbook() {
                    return new XSSFWorkbook();
                }
            };
            writer.write(dataSet, out);
        } catch (DatabaseUnitException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
