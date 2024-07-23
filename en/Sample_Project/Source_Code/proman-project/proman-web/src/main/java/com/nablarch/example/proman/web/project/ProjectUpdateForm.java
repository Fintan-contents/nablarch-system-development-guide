package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.util.DateRelationUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import jakarta.validation.constraints.AssertTrue;
import java.io.Serializable;

/**
 * Project update form.
 *
 * @author Masaya Seko
 */
public class ProjectUpdateForm implements Serializable {

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
    @Domain("clientId")
    private String clientId;

    /**
     * Project manager name
     */
    @Required
    @Domain("userName")
    private String pmKanjiName;

    /**
     * Project leader name
     */
    @Required
    @Domain("userName")
    private String plKanjiName;

    /**
     * Note
     */
    @Domain("note")
    private String note;

    /**
     * Sales
     */
    @Domain("amountOfMoney")
    private String salesAmount;

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
     *
     * @return Division ID
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * Set division ID.
     *
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
    public String getPmKanjiName() {
        return pmKanjiName;
    }

    /**
     * Set project manager name.
     *
     * @param pmKanjiName Project manager name to be set
     */
    public void setPmKanjiName(String pmKanjiName) {
        this.pmKanjiName = pmKanjiName;
    }

    /**
     * Acquires project leader name.
     *
     * @return Project leader name
     */
    public String getPlKanjiName() {
        return plKanjiName;
    }

    /**
     * Set project leader name.
     *
     * @param plKanjiName Project leader name to be set
     */
    public void setPlKanjiName(String plKanjiName) {
        this.plKanjiName = plKanjiName;
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
    public String getSalesAmount() {
        return salesAmount;
    }

    /**
     * Set sales.
     *
     * @param sales Sales to be set
     */
    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * Determines whether the project period (project start date to project end date) is set correctly.
     * If the start date is set as a date later than the end date, the project period is judged to be incorrect.
     * Other settings are accepted.
     *
     * @return false if the start date is set as a date later than the end date, true in other cases (including cases where the start date and/or end date is undetermined)
     */
    @AssertTrue(message = "{com.nablarch.example.app.entity.core.validation.validator.DateRelationUtil.message}")
    public boolean isValidProjectPeriod() {
        return DateRelationUtil.isValid(projectStartDate, projectEndDate);
    }
}
