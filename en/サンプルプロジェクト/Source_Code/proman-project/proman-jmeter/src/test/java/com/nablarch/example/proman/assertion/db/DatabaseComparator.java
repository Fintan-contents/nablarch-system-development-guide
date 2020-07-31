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
import java.sql.SQLException;

/**
 * A class for comparing the state of the database with Excel, which defines the expected values.
 *
 * @author Tanaka Tomoyuki
 */
public class DatabaseComparator {
    private final DatabaseConnection conn;

    /**
     * Constructor.
     * @param conn Database connections to be tested
     */
    public DatabaseComparator(DatabaseConnection conn) {
        this.conn = conn;
    }

    /**
     * Compare the actual database with the Excel file containing the expectations.
     * <p/>
     * Only the tables and columns listed in the expectation file will be compared.
     *
     * @param excelFile Excel file with expectations
     * @throws Exception Unexpected Exceptions
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
