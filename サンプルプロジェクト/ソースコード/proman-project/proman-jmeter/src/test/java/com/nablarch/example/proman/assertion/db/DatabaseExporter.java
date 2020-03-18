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
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseExporter {

    public void export(Connection jdbcConnection, File outputFile) {
        try (OutputStream out = new FileOutputStream(outputFile)) {
            DatabaseConnection conn = new DatabaseConnection(jdbcConnection);
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
