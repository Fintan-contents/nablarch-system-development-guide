package com.nablarch.example.proman.batch.project;


import nablarch.common.dao.EntityUtil;
import nablarch.common.databind.ObjectMapper;
import nablarch.common.databind.ObjectMapperFactory;
import nablarch.core.date.BusinessDateUtil;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.util.DateUtil;
import nablarch.core.util.FilePathSetting;
import nablarch.fw.DataReader;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Result;
import nablarch.fw.Result.Success;
import nablarch.fw.action.BatchAction;
import nablarch.fw.launcher.CommandLine;
import nablarch.fw.reader.DatabaseRecordReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;

/**
 * Batch action class for startup when a project list is output during the applicable period.
 *
 * @author Yutaka Kanayama
 */
public class ExportProjectsInPeriodAction extends BatchAction<SqlRow> {

    /**
     * Output file name.
     */
    private static final String OUTPUT_FILE_NAME = "N21AA002";

    /**
     * Mapper for outputting relevant project information.
     */
    private ObjectMapper<ProjectDto> mapper;

    @Override
    protected void initialize(CommandLine command, ExecutionContext context) {
        FilePathSetting filePathSetting = FilePathSetting.getInstance();
        File output = filePathSetting.getFile("csv_output",
                OUTPUT_FILE_NAME);
        try {
            FileOutputStream outputStream = new FileOutputStream(output);
            this.mapper = ObjectMapperFactory.create(ProjectDto.class, outputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public DataReader<SqlRow> createReader(ExecutionContext context) {
        DatabaseRecordReader reader = new DatabaseRecordReader();
        SqlPStatement statement = getSqlPStatement("FIND_PROJECT_IN_PERIOD");
        Date bizDate = new Date(DateUtil.getDate(BusinessDateUtil.getDate()).getTime());
        statement.setDate(1, bizDate);
        statement.setDate(2, bizDate);
        reader.setStatement(statement);
        return reader;
    }

    @Override
    public Result handle(SqlRow record, ExecutionContext context) {
        ProjectDto dto = EntityUtil.createEntity(ProjectDto.class, record);
        // The following columns cannot be set using EntityUtil as the Dto and type differ. A setter is therefore called explicitly.
        dto.setProjectStartDate(record.getDate("PROJECT_START_DATE"));
        dto.setProjectEndDate(record.getDate("PROJECT_END_DATE"));
        mapper.write(dto);
        return new Success();
    }

    @Override
    protected void terminate(Result result, ExecutionContext context) {
        this.mapper.close();
    }
}
