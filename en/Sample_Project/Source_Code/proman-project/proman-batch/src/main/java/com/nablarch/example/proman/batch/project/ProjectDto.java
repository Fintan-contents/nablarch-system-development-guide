package com.nablarch.example.proman.batch.project;

import nablarch.common.databind.csv.Csv;
import nablarch.common.databind.csv.CsvDataBindConfig;
import nablarch.common.databind.csv.CsvFormat;
import nablarch.core.util.DateUtil;

import java.util.Date;

/**
 * bean class for output of project lists during the applicable period.
 *
 * @author Yutaka Kanayama
 */
@Csv(type = Csv.CsvType.CUSTOM,
        properties = { "projectId", "projectName", "projectType", "projectClass", "projectStartDate",
                "projectEndDate", "organizationId", "clientId", "projectManager", "projectLeader", "note", "sales", "versionNo" },
        headers = { "Project ID", "Project name", "Project type", "Project classification", "Project start date", "Project end date",
                "Organization ID", "Client ID", "Project manager", "Project leader", "Note", "Sales", "Version number" })
@CsvFormat(fieldSeparator = ',', lineSeparator = "\r\n", quote = '\"', ignoreEmptyLine = false,
        requiredHeader = false, charset = "UTF-8", emptyToNull = true, quoteMode = CsvDataBindConfig.QuoteMode.ALL)
public class ProjectDto {
    /** Project ID */
    private String projectId;

    /** Project name */
    private String projectName;

    /** Project type */
    private String projectType;

    /** Project classification */
    private String projectClass;

    /** Project start date */
    private String projectStartDate;

    /** Project end date */
    private String projectEndDate;

    /** Organization ID */
    private String organizationId;

    /** Client ID */
    private String clientId;

    /** Project manager */
    private String projectManager;

    /** Project leader */
    private String projectLeader;

    /** Note */
    private String note;

    /** Sales */
    private String sales;

    /** Version number */
    private String versionNo;

    /**
     * Project ID getter
     * @return Project ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Project ID setter
     * @param projectId Project ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Project name getter
     * @return xx
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Project name setter
     * @param projectNameProject name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Project type getter
     * @return Project type
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Project type setter
     * @param projectType Project type
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * Project classification getter
     * @return Project classification
     */
    public String getProjectClass() {
        return projectClass;
    }

    /**
     * Project classification setter
     * @param projectClass Project classification
     */
    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * Project start date getter
     * @return Project start date
     */
    public String getProjectStartDate() {
        return this.projectStartDate;
    }

    /**
     * Project start date setter
     * @param projectStartDate Project start date
     */
    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = DateUtil.formatDate(projectStartDate, "yyyy/MM/dd");;
    }

    /**
     * Project end date getter
     * @return Project end date
     */
    public String getProjectEndDate() {
        return this.projectEndDate;
    }

    /**
     * Project end date setter
     * @param projectEndDate Project end date
     */
    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = DateUtil.formatDate(projectEndDate, "yyyy/MM/dd");;
    }

    /**
     * Organization ID getter
     * @return Organization ID
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Organization ID setter
     * @param organizationId Organization ID
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Client ID getter
     * @return Client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Client ID setter
     * @param clientId Client ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Project manager getter
     * @return Project manager
     */
    public String getProjectManager() {
        return projectManager;
    }

    /**
     * Project manager setter
     * @param projectManager Project manager
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * Project leader getter
     * @return Project leader
     */
    public String getProjectLeader() {
        return projectLeader;
    }

    /**
     * Project leader setter
     * @param projectLeader Project leader
     */
    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    /**
     * Note getter
     * @return Note
     */
    public String getNote() {
        return note;
    }

    /**
     * Note setter
     * @param note Note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Sales getter
     * @return Sales
     */
    public String getSales() {
        return sales;
    }

    /**
     * Sales setter
     * @param sales Sales
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     * Version number getter
     * @return Version number
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * Version number setter
     * @param versionNo Version number
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}
