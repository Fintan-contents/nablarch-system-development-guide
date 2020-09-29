package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.util.DateRelationUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;

/**
 * Project registration form.
 *
 * @author Masaya Seko
 */
public class ProjectCreateForm implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Project name
     */
    @Required
    @Domain("projectName")
    private String projectName;

    /**
     * Project type
     */
    @Required
    @Domain("projectType")
    private String projectType;

    /**
     * Project classification
     */
    @Required
    @Domain("projectClass")
    private String projectClass;

    /**
     * Project start date
     */
    @Required
    @Domain("date")
    private String projectStartDate;

    /**
     * Project end date
     */
    @Required
    @Domain("date")
    private String projectEndDate;

    /**
     * Division ID
     */
    @Required
    @Domain("organizationId")
    private String divisionId;

    /**
     * Organization ID
     */
    @Required
    @Domain("organizationId")
    private String organizationId;

    /**
     * Client ID
     */
    //TODO: Make this a required item when customer selection is implemented.
    //@Required
    @Domain("id")
    private String clientId;

    /**
     * Project manager name
     */
    @Required
    @Domain("userName")
    private String projectManager;

    /**
     * Project leader name
     */
    @Required
    @Domain("userName")
    private String projectLeader;

    /**
     * Note
     */
    @Domain("note")
    private String note;

    /**
     * Sales
     */
    @Domain("amountOfMoney")
    private String sales;

    /**
     * Acquires project name.
     *
     * @return Project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set project name.
     *
     * @param projectName Project name to be set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Acquires project type.
     *
     * @return Project type
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * Set project type.
     *
     * @param projectType Project type to be set
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * Acquires project classification.
     *
     * @return Project classification
     */
    public String getProjectClass() {
        return projectClass;
    }

    /**
     * Set project classification.
     *
     * @param projectClass Project classification to be set
     */
    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * Acquires project start date.
     *
     * @return Project start date
     */
    public String getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * Set project start date.
     *
     * @param projectStartDate Project start date to be set
     */
    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * Acquires project end date.
     *
     * @return Project end date
     */
    public String getProjectEndDate() {
        return projectEndDate;
    }

    /**
     * Set project end date.
     *
     * @param projectEndDate Project end date to be set
     */
    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    /**
     * Acquires division ID.
     * @return Division ID
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * Set division ID.
     * @param divisionId Division ID to be set
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Acquires department ID.
     *
     * @return department ID
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Set department ID.
     *
     * @param organizationId Department ID to be set
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Acquires client ID.
     *
     * @return Client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Set client ID.
     *
     * @param clientId Client ID to be set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Acquires project manager name.
     *
     * @return Project manager name
     */
    public String getProjectManager() {
        return projectManager;
    }

    /**
     * Set project manager name.
     *
     * @param projectManager Project manager name to be set
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * Acquires project leader name.
     *
     * @return Project leader name
     */
    public String getProjectLeader() {
        return projectLeader;
    }

    /**
     * Set project leader name.
     *
     * @param projectLeader Project leader name to be set
     */
    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    /**
     * Acquires note.
     *
     * @return Note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note.
     *
     * @param note Note to be set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Acquires sales.
     *
     * @return Sales
     */
    public String getSales() {
        return sales;
    }

    /**
     * Set sales.
     *
     * @param sales Sales to be set
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     * Determines whether the project period (project start date to project end date) is set correctly.
     * If the start date is set as a date later than the end date, the project period is judged to be incorrect.
     * Other settings are accepted.
     *
     * @return false if the start date is set as a date later than the end date, true in other cases (including cases where the start date and/or end date is undetermined)
     */
    @AssertTrue(message = "{com.nablarch.example.app.entity.core.validation.validator.DateRelationUtil.message}")
    private boolean isValidProjectPeriod() {
        return DateRelationUtil.isValid(projectStartDate, projectEndDate);
    }

}
