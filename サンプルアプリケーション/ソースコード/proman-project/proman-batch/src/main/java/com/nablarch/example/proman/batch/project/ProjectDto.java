package com.nablarch.example.proman.batch.common.bean;

import nablarch.common.databind.csv.Csv;
import nablarch.common.databind.csv.CsvDataBindConfig;
import nablarch.common.databind.csv.CsvFormat;

/**
 * 期間内プロジェクト一覧出力のbeanクラス。
 *
 * @author TIS
 */
@Csv(type = Csv.CsvType.CUSTOM,
        properties = { "projectId", "projectName", "projectType", "projectClass", "projectStartDate",
                "projectEndDate", "organizationId", "clientId", "projectManager", "projectLeader", "note", "sales", "version" },
        headers = { "プロジェクトID", "プロジェクト名", "プロジェクト種別", "プロジェクト分類", "プロジェクト開始日付", "プロジェクト終了日付",
                "組織ID", "顧客ID", "プロジェクトマネージャー", "プロジェクトリーダー", "備考", "売上高", "バージョン番号" })
@CsvFormat(fieldSeparator = ',', lineSeparator = "\r\n", quote = '\"', ignoreEmptyLine = false,
        requiredHeader = false, charset = "UTF-8", emptyToNull = true, quoteMode = CsvDataBindConfig.QuoteMode.ALL)
public class ProjectDto {
    private String projectId;
    private String projectName;
    private String projectType;
    private String projectClass;
    private String projectStartDate;
    private String projectEndDate;
    private String organizationId;
    private String clientId;
    private String projectManager;
    private String projectLeader;
    private String note;
    private String sales;
    private String version;

    /**
     * プロジェクトIDのgetter
     * @return プロジェクトID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * プロジェクトIDのsetter
     * @param projectId プロジェクトID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * プロジェクト名のgetter
     * @return xx
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名のsetter
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * プロジェクト種別のgetter
     * @return プロジェクト種別
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * プロジェクト種別のsetter
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * プロジェクト分類のgetter
     * @return プロジェクト分類
     */
    public String getProjectClass() {
        return projectClass;
    }

    /**
     * プロジェクト分類のsetter
     * @param projectClass プロジェクト分類
     */
    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * プロジェクト開始日付のgetter
     * @return プロジェクト開始日付
     */
    public String getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * プロジェクト開始日付のsetter
     * @param projectStartDate プロジェクト開始日付
     */
    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * プロジェクト終了日付のgetter
     * @return プロジェクト終了日付
     */
    public String getProjectEndDate() {
        return projectEndDate;
    }

    /**
     * プロジェクト終了日付のsetter
     * @param projectEndDate プロジェクト終了日付
     */
    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    /**
     * 組織IDのgetter
     * @return 組織ID
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 組織IDのsetter
     * @param organizationId 組織ID
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 顧客IDのgetter
     * @return 顧客ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 顧客IDのsetter
     * @param clientId 顧客ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * プロジェクトマネージャーのgetter
     * @return プロジェクトマネージャー
     */
    public String getProjectManager() {
        return projectManager;
    }

    /**
     * プロジェクトマネージャーのsetter
     * @param projectManager プロジェクトマネージャー
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * プロジェクトリーダーのgetter
     * @return プロジェクトリーダー
     */
    public String getProjectLeader() {
        return projectLeader;
    }

    /**
     * プロジェクトリーダーのsetter
     * @param projectLeader プロジェクトリーダー
     */
    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    /**
     * 備考のgetter
     * @return 備考
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考のsetter
     * @param note 備考
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 売上高のgetter
     * @return 売上高
     */
    public String getSales() {
        return sales;
    }

    /**
     * 売上高のsetter
     * @param sales 売上高
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     * バージョン番号のgetter
     * @return バージョン番号
     */
    public String getVersion() {
        return version;
    }

    /**
     * バージョン番号のsetter
     * @param version バージョン番号
     */
    public void setVersion(String version) {
        this.version = version;
    }
}