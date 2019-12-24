package com.nablarch.example.proman.web.project;

import java.io.Serializable;
import java.sql.Date;

/**
 * プロジェクト検索条件DTO
 */
public class ProjectSearchConditionDto implements Serializable {
    /** 事業部 */
    private Integer divisionId;
    /** 部門 */
    private Integer organizationId;
    /** プロジェクト種別 */
    private String[] projectType;
    /** プロジェクト分類 */
    private String[] projectClass;
    /** 売上高_実績_FROM */
    private Integer salesFrom;
    /** 売上高_実績_TO */
    private Integer salesTo;
    /** 開始日_FROM */
    private Date projectStartDateFrom;
    /** 開始日_TO */
    private Date projectStartDateTo;
    /** 終了日_FROM */
    private Date projectEndDateFrom;
    /** 終了日_TO */
    private Date projectEndDateTo;
    /** プロジェクト名 */
    private String projectName;
    /** ページ番号 */
    private long pageNumber;

    /**
     * 事業部 を取得する。
     *
     * @return 事業部
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部 を設定する
     *
     * @param divisionId 事業部
     */
    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 部門 を取得する。
     *
     * @return 部門
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門 を設定する
     *
     * @param organizationId 部門
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * プロジェクト種別 を取得する。
     *
     * @return プロジェクト種別
     */
    public String[] getProjectType() {
        return projectType;
    }

    /**
     * プロジェクト種別 を設定する
     *
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String[] projectType) {
        this.projectType = projectType;
    }

    /**
     * プロジェクト分類 を取得する。
     *
     * @return プロジェクト分類
     */
    public String[] getProjectClass() {
        return projectClass;
    }

    /**
     * プロジェクト分類 を設定する
     *
     * @param projectClass プロジェクト分類
     */
    public void setProjectClass(String[] projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * 売上高_実績_FROM を取得する。
     *
     * @return 売上高_実績_FROM
     */
    public Integer getSalesFrom() {
        return salesFrom;
    }

    /**
     * 売上高_実績_FROM を設定する
     *
     * @param salesFrom 売上高_実績_FROM
     */
    public void setSalesFrom(Integer salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * 売上高_実績_TO を取得する。
     *
     * @return 売上高_実績_TO
     */
    public Integer getSalesTo() {
        return salesTo;
    }

    /**
     * 売上高_実績_TO を設定する
     *
     * @param salesTo 売上高_実績_TO
     */
    public void setSalesTo(Integer salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * 開始日_FROM を取得する。
     *
     * @return 開始日_FROM
     */
    public Date getProjectStartDateFrom() {
        return projectStartDateFrom;
    }

    /**
     * 開始日_FROM を設定する
     *
     * @param projectStartDateFrom 開始日_FROM
     */
    public void setProjectStartDateFrom(Date projectStartDateFrom) {
        this.projectStartDateFrom = projectStartDateFrom;
    }

    /**
     * 開始日_TO を取得する。
     *
     * @return 開始日_TO
     */
    public Date getProjectStartDateTo() {
        return projectStartDateTo;
    }

    /**
     * 開始日_TO を設定する
     *
     * @param projectStartDateTo 開始日_TO
     */
    public void setProjectStartDateTo(Date projectStartDateTo) {
        this.projectStartDateTo = projectStartDateTo;
    }

    /**
     * 終了日_FROM を取得する。
     *
     * @return 終了日_FROM
     */
    public Date getProjectEndDateFrom() {
        return projectEndDateFrom;
    }

    /**
     * 終了日_FROM を設定する
     *
     * @param projectEndDateFrom 終了日_FROM
     */
    public void setProjectEndDateFrom(Date projectEndDateFrom) {
        this.projectEndDateFrom = projectEndDateFrom;
    }

    /**
     * 終了日_TO を取得する。
     *
     * @return 終了日_TO
     */
    public Date getProjectEndDateTo() {
        return projectEndDateTo;
    }

    /**
     * 終了日_TO を設定する
     *
     * @param projectEndDateTo 終了日_TO
     */
    public void setProjectEndDateTo(Date projectEndDateTo) {
        this.projectEndDateTo = projectEndDateTo;
    }

    /**
     * プロジェクト名 を取得する。
     *
     * @return プロジェクト名
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名 を設定する
     *
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * ページ番号 を取得する。
     *
     * @return ページ番号
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * ページ番号 を設定する
     *
     * @param pageNumber ページ番号
     */
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }
}
