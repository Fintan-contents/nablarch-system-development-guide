package com.nablarch.example.proman.batch.service;

import com.nablarch.example.proman.batch.common.bean.ProjectDto;
import nablarch.core.util.DateUtil;

/**
 * プロジェクトサービス
 *
 * @author TIS
 */
public class ProjectService {

    /**
     * プロジェクトをcsvとして出力するためのフォーマット
     *
     * @param record フォーマット対象マップ
     * @return フォーマットしたプロジェクト
     */
    public ProjectDto formatProjectForCsv(ProjectDto record) {
        record.setProjectStartDate(DateUtil.formatDate(DateUtil.getParsedDate(record.getProjectStartDate(), "yyyy-MM-dd"), "yyyy/MM/dd"));
        record.setProjectEndDate(DateUtil.formatDate(DateUtil.getParsedDate(record.getProjectEndDate(), "yyyy-MM-dd"), "yyyy/MM/dd"));

        return record;
    }

}
