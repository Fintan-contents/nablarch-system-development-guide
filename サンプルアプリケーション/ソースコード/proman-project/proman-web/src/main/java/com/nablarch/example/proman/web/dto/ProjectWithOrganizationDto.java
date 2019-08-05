package com.nablarch.example.proman.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * プロジェクト情報のDto
 *
 * @author Nabu Rakutaro
 */
public class ProjectWithOrganizationDto implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * プロジェクトID
     */
    private Integer projectId;

    /**
     * 事業部ID
     */
    private Integer divisionId;

    /**
     * 事業部名
     */
    private String divisionName;

    /**
     * 部門ID
     */
    private Integer organizationId;

    /**
     * 部門ID
     */
    private String organizationName;

    /**
     * プロジェクト名
     */
    private String projectName;

    /**
     * プロジェクト種別
     */
    private String projectType;

    /**
     * プロジェクト分類
     */
    private String projectClass;

    /**
     * プロジェクト開始日付
     */
    private Date projectStartDate;

    /**
     * プロジェクト終了日付
     */
    private Date projectEndDate;

    /**
     * 顧客ID
     */
    private Integer clientId;

    /**
     * プロジェクトマネージャー
     */
    private String projectManager;

    /**
     * プロジェクトリーダー
     */
    private String projectLeader;

    /**
     * 備考
     */
    private String note;

    /**
     * 売上高
     */
    private Integer sales;

    /**
     * バージョン番号
     */
    private Long version;

    /**
     * プロジェクトIDを取得する。
     *
     * @return プロジェクトID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * プロジェクトIDを設定する。
     *
     * @param projectId プロジェクトID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 事業部IDを取得する。
     *
     * @return 事業部ID
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部IDを設定する。
     *
     * @param divisionId 事業部ID
     */
    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 事業部名を取得する。
     *
     * @return 事業部名
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * 事業部名を設定する。
     *
     * @param divisionName 事業部名
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * 部門IDを取得する。
     *
     * @return 部門ID
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門IDを設定する。
     *
     * @param organizationId 部門ID
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 部門名を取得する。
     *
     * @return 部門名
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * 部門名を設定する。
     *
     * @param organizationName 部門名
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
    public String getProjectType() {
        return projectType;
    }

    /**
     * プロジェクト種別を設定する。
     *
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * プロジェクト分類を取得する。
     *
     * @return プロジェクト分類
     */
    public String getProjectClass() {
        return projectClass;
    }

    /**
     * プロジェクト分類を設定する。
     *
     * @param projectClass プロジェクト分類
     */
    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * プロジェクト開始日付を取得する。
     *
     * @return プロジェクト開始日付
     */
    public Date getProjectStartDate() {
        return projectStartDate == null ? null : (Date) projectStartDate.clone();
    }

    /**
     * プロジェクト開始日付を設定する。
     *
     * @param projectStartDate プロジェクト開始日付
     */
    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate == null ? null : (Date) projectStartDate.clone();
    }

    /**
     * プロジェクト終了日付を取得する。
     *
     * @return プロジェクト終了日付
     */
    public Date getProjectEndDate() {
        return projectEndDate == null ? null : (Date) projectEndDate.clone();
    }

    /**
     * プロジェクト終了日付を設定する。
     *
     * @param projectEndDate プロジェクト終了日付
     */
    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate == null ? null : (Date) projectEndDate.clone();
    }

    /**
     * 顧客IDを取得する。
     *
     * @return 顧客ID
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 顧客IDを設定する。
     *
     * @param clientId 顧客ID
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * プロジェクトマネージャーを取得する。
     *
     * @return プロジェクトマネージャー
     */
    public String getProjectManager() {
        return projectManager;
    }

    /**
     * プロジェクトマネージャーを設定する。
     *
     * @param projectManager プロジェクトマネージャー
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * プロジェクトリーダーを取得する。
     *
     * @return プロジェクトリーダー
     */
    public String getProjectLeader() {
        return projectLeader;
    }

    /**
     * プロジェクトリーダーを設定する。
     *
     * @param projectLeader プロジェクトリーダー
     */
    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    /**
     * 備考を取得する。
     *
     * @return 備考
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考を設定する。
     *
     * @param note 備考
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 売上高を取得する。
     *
     * @return 売上高
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * 売上高を設定する。
     *
     * @param sales 売上高
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * バージョン番号を取得する。
     *
     * @return バージョン番号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * バージョン番号を設定する。
     *
     * @param version バージョン番号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 顧客IDを保持しているか否かを返す。
     *
     * @return trueの場合は、顧客IDを保持している。
     */
    public boolean hasClientId() {
        return clientId != null;
    }
}
