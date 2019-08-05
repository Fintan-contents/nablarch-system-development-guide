package com.nablarch.example.proman.web.form;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * 処理対象パラメータ。
 *
 * @author Nabu Rakutaro
 */
public class ProjectSearchInitialForm implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * プロジェクトID
     */
    @Required
    @Domain("id")
    private String projectId;

    /**
     * プロジェクトIDを取得する。
     *
     * @return プロジェクトID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * プロジェクトIDを設定する。
     *
     * @param projectId 設定するプロジェクトID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
