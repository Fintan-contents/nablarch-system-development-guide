package com.nablarch.example.proman.web.project;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.validator.Required;

import java.io.Serializable;

/**
 * プロジェクト詳細フォーム
 */
public class ProjectDetailInitialForm implements Serializable {
    /** プロジェクトID */
    @Required
    @Domain("projectId")
    private String projectId;

    /**
     * プロジェクトID を取得する。
     *
     * @return プロジェクトID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * プロジェクトID を設定する
     *
     * @param projectId プロジェクトID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
