package com.nablarch.example.proman.web.project;

import java.io.Serializable;
import java.sql.Date;

/**
 * Project search criteria DTO
 */
public class ProjectSearchConditionDto implements Serializable {
    /** Division ID */
    private Integer divisionId;
    /** Organization Id */
    private Integer organizationId;
    /** Project type */
    private String[] projectType;
    /** Project classification */
    private String[] projectClass;
    /** Sales from */
    private Integer salesFrom;
    /** Sales to */
    private Integer salesTo;
    /** Start date from */
    private Date projectStartDateFrom;
    /** Start date to */
    private Date projectStartDateTo;
    /** Ebd date from */
    private Date projectEndDateFrom;
    /** End date to */
    private Date projectEndDateTo;
    /** Project name */
    private String projectName;
    /** Page Number */
    private long pageNumber;

    /**
     * Acquires division ID.
     *
     * @return division ID
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * Set division ID.
     *
     * @param divisionId division ID
     */
    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Acquires organization Id 
     *
     * @return organization Id
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * Set organization Id.
     *
     * @param organizationId organization Id
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Acquires project type.
     *
     * @return project type
     */
    public String[] getProjectType() {
        return projectType;
    }

    /**
     * Set project type.
     *
     * @param projectType project type
     */
    public void setProjectType(String[] projectType) {
        this.projectType = projectType;
    }

    /**
     * Acquires project classification.
     *
     * @return project classification
     */
    public String[] getProjectClass() {
        return projectClass;
    }

    /**
     * Set project classification.
     *
     * @param projectClass project classification
     */
    public void setProjectClass(String[] projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * Acquires sales from.
     *
     * @return sales from
     */
    public Integer getSalesFrom() {
        return salesFrom;
    }

    /**
     * Set sales from.
     *
     * @param salesFrom sales from
     */
    public void setSalesFrom(Integer salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * Acquires sales to.
     *
     * @return sales to
     */
    public Integer getSalesTo() {
        return salesTo;
    }

    /**
     * Set sales to.
     *
     * @param salesTo sales to
     */
    public void setSalesTo(Integer salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * Acquires start date from.
     *
     * @return start date from
     */
    public Date getProjectStartDateFrom() {
        return projectStartDateFrom;
    }

    /**
     * Set start date from.
     *
     * @param projectStartDateFrom start date from.
     */
    public void setProjectStartDateFrom(Date projectStartDateFrom) {
        this.projectStartDateFrom = projectStartDateFrom;
    }

    /**
     * Acquires start date to.
     *
     * @return start date to
     */
    public Date getProjectStartDateTo() {
        return projectStartDateTo;
    }

    /**
     * Set start date to.
     *
     * @param projectStartDateTo start date to
     */
    public void setProjectStartDateTo(Date projectStartDateTo) {
        this.projectStartDateTo = projectStartDateTo;
    }

    /**
     * Acquires end date from.
     *
     * @return end date from
     */
    public Date getProjectEndDateFrom() {
        return projectEndDateFrom;
    }

    /**
     * Set end date from.
     *
     * @param projectEndDateFrom end date from
     */
    public void setProjectEndDateFrom(Date projectEndDateFrom) {
        this.projectEndDateFrom = projectEndDateFrom;
    }

    /**
     * Acquires end date to.
     *
     * @return end date to
     */
    public Date getProjectEndDateTo() {
        return projectEndDateTo;
    }

    /**
     * Set end date to.
     *
     * @param projectEndDateTo end date to
     */
    public void setProjectEndDateTo(Date projectEndDateTo) {
        this.projectEndDateTo = projectEndDateTo;
    }

    /**
     * Acquires project name.
     *
     * @return project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set project name.
     *
     * @param projectName project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Acquires page number.
     *
     * @return page number
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * Set page number.
     *
     * @param pageNumber page number
     */
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }
}
