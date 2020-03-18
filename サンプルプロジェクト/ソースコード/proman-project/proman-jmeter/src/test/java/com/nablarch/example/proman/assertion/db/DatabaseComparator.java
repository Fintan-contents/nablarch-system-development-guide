package com.nablarch.example.proman.assertion.db;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * データベースの状態と期待値を定義したExcelを比較するためのクラス。
 *
 * @author Tanaka Tomoyuki
 */
public class DatabaseComparator {

    private final DatabaseConnection conn;

    /**
     * コンストラクタ。
     * @param jdbcConnection テスト対象のデータベースコネクション
     */
    public DatabaseComparator(Connection jdbcConnection) {
        try {
            this.conn = new DatabaseConnection(jdbcConnection);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 期待値が記載されたExcelファイルと実際のデータベースを比較する。
     * <p/>
     * 期待値のファイルに記載されたテーブル、カラムのみが比較対象となる。
     *
     * @param excelFile 期待値が記載されたExcelファイル
     * @throws Exception 予期しない例外
     */
    public void compare(File excelFile) throws Exception {
        compare(new XlsDataSet(excelFile));
    }

    private void compare(IDataSet expectedDataSet) throws DatabaseUnitException, SQLException {
        String[] tableNames = expectedDataSet.getTableNames();
        IDataSet actualDataSet = conn.createDataSet(tableNames);
        ITableIterator itr = expectedDataSet.iterator();
        while (itr.next()) {
            ITable expectedTable = itr.getTable();
            String tableName = expectedTable.getTableMetaData().getTableName();
            ITable actualTable = actualDataSet.getTable(tableName);
            compare(expectedTable, actualTable);
        }
    }

    private void compare(ITable expected, ITable actual) throws DatabaseUnitException {
        Column[] columns = expected.getTableMetaData().getColumns();
        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actual, columns);
        Assertion.assertEquals(expected, filteredTable);
    }
}
