package com.nablarch.example.proman.web.form;

import nablarch.core.validation.ee.Domain;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * プロジェクト検索一覧フォーム
 *
 * @author Nabu Rakutaro
 */
public class ProjectSearchForm extends SearchFormBase implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 事業部
     */
    @Domain("organizationId")
    private String divisionId;

    /**
     * 部門
     */
    @Domain("organizationId")
    private String organizationId;

    /**
     * プロジェクト名
     */
    @Domain("projectName")
    private String projectName;

    /**
     * プロジェクト種別
     */
    @Valid
    private String[] projectType;

    /**
     * プロジェクト分類
     */
    @Valid
    private String[] projectClass;

    /**
     * 売上高_実績（FROM）
     */
    @Domain("amountOfMoney")
    private String salesFrom;

    /**
     * 売上高_実績（TO）
     */
    @Domain("amountOfMoney")
    private String salesTo;

    /**
     * プロジェクト開始日（FROM）
     */
    @Domain("date")
    private String projectStartDateBegin;

    /**
     * プロジェクト開始日（TO）
     */
    @Domain("date")
    private String projectStartDateEnd;

    /**
     * プロジェクト終了日（FROM）
     */
    @Domain("date")
    private String projectEndDateBegin;

    /**
     * プロジェクト終了日（TO）
     */
    @Domain("date")
    private String projectEndDateEnd;

    /**
     * 事業部を取得する。
     *
     * @return 事業部
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部を設定する。
     *
     * @param divisionId 事業部
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 部門を取得する。
     *
     * @return 部門
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門を設定する。
     *
     * @param organizationId 部門
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * プロジェクト名を取得する。
     *
     * @return プロジェクト名
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名を設定する。
     *
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * プロジェクト種別を取得する。
     *
     * @return プロジェクト種別
     */
    public String[] getProjectType() {
        return projectType == null ? null : (String[]) projectType.clone();
    }

    /**
     * プロジェクト種別を設定する。
     *
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String[] projectType) {
        this.projectType = projectType == null ? null : (String[]) projectType.clone();
    }

    /**
     * プロジェクト分類を取得する。
     *
     * @return プロジェクト分類
     */
    public String[] getProjectClass() {
        return projectClass == null ? null : (String[]) projectClass.clone();
    }

    /**
     * プロジェクト分類を設定する。
     *
     * @param projectClass プロジェクト分類
     */
    public void setProjectClass(String[] projectClass) {
        this.projectClass = projectClass == null ? null : (String[]) projectClass.clone();
    }

    /**
     * 売上高_実績（FROM）を取得する。
     *
     * @return 売上高_実績（FROM）
     */
    public String getSalesFrom() {
        return this.salesFrom;
    }

    /**
     * 売上高_実績（FROM）を設定する。
     *
     * @param salesFrom 売上高_実績（FROM）をセットする。
     */
    public void setSalesFrom(String salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * 売上高_実績（FROM）を取得する。
     *
     * @return 売上高_実績（TO）
     */
    public String getSalesTo() {
        return this.salesTo;
    }

    /**
     * 売上高_実績（TO）を設定する。
     *
     * @param salesTo 売上高_実績（TO）をセットする。
     */
    public void setSalesTo(String salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * プロジェクト開始日（FROM）を返す。
     *
     * @return プロジェクト開始日（FROM）
     */
    public String getProjectStartDateBegin() {
        return projectStartDateBegin;
    }

    /**
     * プロジェクト開始日（FROM）文字列をセットする。
     *
     * @param projectStartDateBegin プロジェクト開始日（FROM）
     */
    public void setProjectStartDateBegin(String projectStartDateBegin) {
        this.projectStartDateBegin = projectStartDateBegin;
    }

    /**
     * プロジェクト開始日（TO）を返す。
     *
     * @return プロジェクト開始日（TO）
     */
    public String getProjectStartDateEnd() {
        return projectStartDateEnd;
    }

    /**
     * プロジェクト開始日（TO）文字列をセットする。
     *
     * @param projectStartDateEnd プロジェクト開始日（TO）
     */
    public void setProjectStartDateEnd(String projectStartDateEnd) {
        this.projectStartDateEnd = projectStartDateEnd;
    }

    /**
     * プロジェクト終了日（FROM）を返す。
     *
     * @return プロジェクト終了日（FROM）
     */
    public String getProjectEndDateBegin() {
        return projectEndDateBegin;
    }

    /**
     * プロジェクト終了日（FROM）文字列をセットする。
     *
     * @param projectEndDateBegin プロジェクト終了日（FROM）
     */
    public void setProjectEndDateBegin(String projectEndDateBegin) {
        this.projectEndDateBegin = projectEndDateBegin;
    }

    /**
     * プロジェクト終了日（TO）を返す。
     *
     * @return プロジェクト終了日（TO）
     */
    public String getProjectEndDateEnd() {
        return projectEndDateEnd;
    }

    /**
     * プロジェクト終了日（TO）文字列をセットする。
     *
     * @param projectEndDateEnd プロジェクト終了日（TO）
     */
    public void setProjectEndDateEnd(String projectEndDateEnd) {
        this.projectEndDateEnd = projectEndDateEnd;
    }
}
