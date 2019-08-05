package com.nablarch.example.proman.web.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * プロジェクト検索のDto
 *
 * @author TIS
 */
public class ProjectWithOrganizationSearchConditionDto implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 事業部
     */
    private Integer divisionId;

    /**
     * 部門
     */
    private Integer organizationId;

    /**
     * プロジェクト名
     */
    private String projectName;

    /**
     * プロジェクト種別
     */
    private String[] projectType;

    /**
     * プロジェクト分類
     */
    private String[] projectClass;

    /**
     * 売上高_実績（FROM）
     */
    private Integer salesFrom;

    /**
     * 売上高_実績（TO）
     */
    private Integer salesTo;

    /**
     * プロジェクト開始日付(FROM）
     */
    private Date projectStartDateBegin;

    /**
     * プロジェクト開始日付(TO）
     */
    private Date projectStartDateEnd;

    /**
     * プロジェクト終了日付（FROM)
     */
    private Date projectEndDateBegin;

    /**
     * プロジェクト終了日付（TO)
     */
    private Date projectEndDateEnd;

    /**
     * ページ番号
     */
    private Integer pageNumber;

    /**
     * 事業部を取得する。
     *
     * @return 事業部
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部を設定する。
     *
     * @param divisionId 事業部
     */
    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 部門を取得する。
     *
     * @return 部門
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門を設定する。
     *
     * @param organizationId 部門
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * projectNameを返却する。
     *
     * @return projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * projectNameを設定する。
     *
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * projectTypeを返却する。
     *
     * @return projectType
     */
    public String[] getProjectType() {
        return projectType == null ? null : (String[]) projectType.clone();
    }

    /**
     * projectTypeを設定する。
     *
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String[] projectType) {
        this.projectType = projectType == null ? null : (String[]) projectType.clone();
    }

    /**
     * projectClassを返却する。
     *
     * @return projectClass
     */
    public String[] getProjectClass() {
        return projectClass == null ? null : (String[]) projectClass.clone();
    }

    /**
     * projectClassを設定する。
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
    public Integer getSalesFrom() {
        return this.salesFrom;
    }

    /**
     * 売上高_実績（FROM）を設定する。
     *
     * @param salesFrom 売上高_実績（FROM）をセットする。
     */
    public void setSalesFrom(Integer salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * 売上高_実績（FROM）を取得する。
     *
     * @return 売上高_実績（TO）
     */
    public Integer getSalesTo() {
        return this.salesTo;
    }

    /**
     * 売上高_実績（TO）を設定する。
     *
     * @param salesTo 売上高_実績（TO）をセットする。
     */
    public void setSalesTo(Integer salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * projectStartDateBeginを返却する。
     *
     * @return projectStartDateBegin
     */
    public Date getProjectStartDateBegin() {
        return projectStartDateBegin == null ? null : (Date) projectStartDateBegin.clone();
    }

    /**
     * projectStartDateBeginを設定する。
     *
     * @param projectStartDateBegin プロジェクト開始日（From）
     */
    public void setProjectStartDateBegin(Date projectStartDateBegin) {
        this.projectStartDateBegin = projectStartDateBegin == null ? null : (Date) projectStartDateBegin.clone();
    }

    /**
     * projectStartDateEndを返却する。
     *
     * @return projectStartDateEnd
     */
    public Date getProjectStartDateEnd() {
        return projectStartDateEnd == null ? null : (Date) projectStartDateEnd.clone();
    }

    /**
     * projectStartDateEndを設定する。
     *
     * @param projectStartDateEnd プロジェクト開始日（To）
     */
    public void setProjectStartDateEnd(Date projectStartDateEnd) {
        this.projectStartDateEnd = projectStartDateEnd == null ? null : (Date) projectStartDateEnd.clone();
    }

    /**
     * projectEndDateBeginを返却する。
     *
     * @return projectEndDateBegin
     */
    public Date getProjectEndDateBegin() {
        return projectEndDateBegin == null ? null : (Date) projectEndDateBegin.clone();
    }

    /**
     * projectEndDateBeginを設定する。
     *
     * @param projectEndDateBegin プロジェクト終了日（From)
     */
    public void setProjectEndDateBegin(Date projectEndDateBegin) {
        this.projectEndDateBegin = projectEndDateBegin == null ? null : (Date) projectEndDateBegin.clone();
    }

    /**
     * projectEndDateEndを返却する。
     *
     * @return projectEndDateEnd
     */
    public Date getProjectEndDateEnd() {
        return projectEndDateEnd == null ? null : (Date) projectEndDateEnd.clone();
    }

    /**
     * projectEndDateEndを設定する。
     *
     * @param projectEndDateEnd プロジェクト終了日（To）
     */
    public void setProjectEndDateEnd(Date projectEndDateEnd) {
        this.projectEndDateEnd = projectEndDateEnd == null ? null : (Date) projectEndDateEnd.clone();
    }

    /**
     * pageNumberを返却する。
     *
     * @return ページ番号
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * pageNumber設定する。
     *
     * @param pageNumber ページ番号
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
