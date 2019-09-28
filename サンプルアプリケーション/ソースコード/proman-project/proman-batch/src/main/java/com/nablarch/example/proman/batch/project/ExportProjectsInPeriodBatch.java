package com.nablarch.example.proman.batch.project;


import nablarch.common.dao.EntityUtil;
import nablarch.common.databind.ObjectMapper;
import nablarch.common.databind.ObjectMapperFactory;
import nablarch.core.date.BusinessDateUtil;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
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
 * 期間内プロジェクト一覧出力の都度起動バッチアクションクラス。
 *
 * @author TIS
 */
public class ExportProjectsInPeriodBatch extends BatchAction<SqlRow> {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerManager.get(ExportProjectsInPeriodBatch.class);

    /**
     * 出力ファイル名。
     */
    private static final String OUTPUT_FILE_NAME = "projectsInPeriod";

    /**
     * ObjectMapper。
     */
    private ObjectMapper<ProjectDto> mapper;

    @Override
    protected void initialize(CommandLine command, ExecutionContext context) {
        FilePathSetting filePathSetting = FilePathSetting.getInstance();
        File output = filePathSetting.getFile("output",
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
        mapper.write(EntityUtil.createEntity(ProjectDto.class, record));
        return new Success();
    }

    @Override
    protected void terminate(Result result, ExecutionContext context) {
        this.mapper.close();
    }
}
