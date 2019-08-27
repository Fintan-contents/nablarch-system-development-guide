package com.nablarch.example.proman.batch.action;

import com.nablarch.example.proman.batch.common.bean.ProjectDto;
import com.nablarch.example.proman.batch.service.ProjectService;
import nablarch.common.dao.EntityUtil;
import nablarch.common.databind.ObjectMapper;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.fw.DataReader;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Result;
import nablarch.fw.Result.Success;
import nablarch.fw.action.BatchAction;
import nablarch.fw.launcher.CommandLine;
import nablarch.fw.reader.DatabaseRecordReader;


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
     * 出力ファイルPREFIX。
     */
    private static final String OUTPUT_FILE_NAME_PREFIX = "projectsInPeriod";

    /**
     * レイアウトファイル。
     */
    private static final String LAYOUT_FILE_NAME = "PROJECTS_IN_PERIOD";

    /**
     * ObjectMapper。
     */
    private ObjectMapper<ProjectDto> mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize(CommandLine command, ExecutionContext context) {
        LOGGER.logInfo("期間内プロジェクト一覧の出力処理を開始します。");

//        FilePathSetting filePathSetting = FilePathSetting.getInstance();
//        File output = filePathSetting.getFile("db_to_file_output",
//                OUTPUT_FILE_NAME_PREFIX + "_" + DateUtil.formatDate(new Date(), "yyyyMMdd"));
//        try {
//            FileOutputStream outputStream = new FileOutputStream(output);
//            this.mapper = ObjectMapperFactory.create(ProjectDto.class, outputStream);
//        } catch (FileNotFoundException e) {
//            throw new ApplicationException(MessageUtil.createMessage(
//                    MessageLevel.ERROR, "xxxx"));
//        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataReader<SqlRow> createReader(ExecutionContext executionContext) {
        DatabaseRecordReader reader = new DatabaseRecordReader();
        SqlPStatement statement = getSqlPStatement("FIND_PROJECT_IN_PERIOD");
        reader.setStatement(statement);
        return reader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result handle(SqlRow record, ExecutionContext executionContext) {
        ProjectService service = new ProjectService();
        mapper.write(service.formatProjectForCsv(EntityUtil.createEntity(ProjectDto.class, record)));
        return new Success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void terminate(Result result, ExecutionContext context) {
        this.mapper.close();
        if (result.isSuccess()) {
            LOGGER.logInfo("期間内プロジェクト一覧の出力処理が完了しました。");
        }
    }
}
