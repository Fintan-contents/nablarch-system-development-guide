package com.nablarch.example.proman.web.form;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * プロジェクトID格納フォーム。
 *
 * @author Nabu Rakutaro
 */
public class ProjectUpdateInitialForm implements Serializable {

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
     * @return プロジェクトId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * プロジェクトIdを設定する。
     *
     * @param projectId 設定するプロジェクトId
     */

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
