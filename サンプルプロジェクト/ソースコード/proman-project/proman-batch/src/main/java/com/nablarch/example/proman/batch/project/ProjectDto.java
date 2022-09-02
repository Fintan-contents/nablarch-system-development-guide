package com.nablarch.example.proman.batch.project;

import nablarch.common.databind.csv.Csv;
import nablarch.common.databind.csv.CsvDataBindConfig;
import nablarch.common.databind.csv.CsvFormat;
import nablarch.core.util.DateUtil;

import java.util.Date;

/**
 * 期間内プロジェクト一覧出力のbeanクラス。
 *
 * @author Yutaka Kanayama
 */
@Csv(type = Csv.CsvType.CUSTOM,
        properties = { "projectId", "projectName", "projectType", "projectClass", "projectStartDate",
                "projectEndDate", "organizationId", "clientId", "projectManager", "projectLeader", "note", "sales", "versionNo" },
        headers = { "プロジェクトID", "プロジェクト名", "プロジェクト種別", "プロジェクト分類", "プロジェクト開始日付", "プロジェクト終了日付",
                "組織ID", "顧客ID", "プロジェクトマネージャー", "プロジェクトリーダー", "備考", "売上高", "バージョン番号" })
@CsvFormat(fieldSeparator = ',', lineSeparator = "\r\n", quote = '\"', ignoreEmptyLine = false,
        requiredHeader = false, charset = "UTF-8", emptyToNull = true, quoteMode = CsvDataBindConfig.QuoteMode.ALL)
public class ProjectDto {
    /** プロジェクトID */
    private String projectId;

    /** プロジェクト名 */
    private String projectName;

    /** プロジェクト種別 */
    private String projectType;

    /** プロジェクト分類 */
    private String projectClass;

    /** プロジェクト開始日付 */
    private String projectStartDate;

    /** プロジェクト終了日付 */
    private String projectEndDate;

    /** 組織ID */
    private String organizationId;

    /** 顧客ID */
    private String clientId;

    /** プロジェクトマネージャー */
    private String projectManager;

    /** プロジェクトリーダー */
    private String projectLeader;

    /** 備考 */
    private String note;

    /** 売上高 */
    private String sales;

    /** バージョン番号 */
    private String versionNo;

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
        return this.projectStartDate;
    }

    /**
     * プロジェクト開始日付のsetter
     * @param projectStartDate プロジェクト開始日付
     */
    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = DateUtil.formatDate(projectStartDate, "yyyy/MM/dd");;
    }

    /**
     * プロジェクト終了日付のgetter
     * @return プロジェクト終了日付
     */
    public String getProjectEndDate() {
        return this.projectEndDate;
    }

    /**
     * プロジェクト終了日付のsetter
     * @param projectEndDate プロジェクト終了日付
     */
    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = DateUtil.formatDate(projectEndDate, "yyyy/MM/dd");;
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
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * バージョン番号のsetter
     * @param versionNo バージョン番号
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}